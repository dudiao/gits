package xyz.gits.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import xyz.gits.boot.api.system.dto.UserUpdateDTO;
import xyz.gits.boot.api.system.dto.UserSaveDTO;
import xyz.gits.boot.api.system.entity.User;
import xyz.gits.boot.api.system.entity.UserRoleRel;
import xyz.gits.boot.api.system.enums.LockFlag;
import xyz.gits.boot.api.system.enums.StopFlag;
import xyz.gits.boot.api.system.vo.UserVO;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.constants.CacheConstants;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.common.security.UserUtil;
import xyz.gits.boot.system.mapper.UserMapper;
import xyz.gits.boot.system.service.IUserRoleRelService;
import xyz.gits.boot.system.service.IUserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Slf4j
public class UserServiceImpl extends BasicServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRoleRelService userRoleRelService;

    /**
     * 分页获取用户列表
     *
     * @param queryWrapper
     * @return
     */
    @Override
    public IPage<User> getPage(Wrapper<User> queryWrapper) {
        if (ObjectUtil.isEmpty(queryWrapper)) {
            return page(parseParameter());
        }
        return page(queryWrapper);
    }

    @Override
    public User getByUsername(String userName) {
        return baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, userName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO saveUser(UserSaveDTO dto) {
        User user = new User();
        BeanUtils.copyPropertiesIgnoreNull(dto, user);

        user.setPwdLockFlag(LockFlag.UN_LOCKED);
        user.setStopFlag(StopFlag.ENABLE);
        if (StrUtil.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // TODO 默认密码需要从配置中心读取
            user.setPassword(passwordEncoder.encode("111111-a"));
        }
        baseMapper.insert(user);

        UserVO userVO = new UserVO();
        BeanUtils.copyPropertiesIgnoreNull(user, userVO);

        // 保存用户的角色
        if (CollUtil.isEmpty(dto.getRole())) {
            return userVO;
        }
        List<UserRoleRel> userRoleList = dto.getRole()
            .stream().map(roleId -> {
                UserRoleRel userRole = new UserRoleRel();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(roleId);
                return userRole;
            }).collect(Collectors.toList());
        userRoleRelService.saveBatch(userRoleList);

        // TODO 缺少用户权限
        userVO.setRoles(new HashSet<>(dto.getRole()));
        return userVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(name= CacheConstants.LOGIN_USER)
    public void updateUser(UserUpdateDTO userDTO) {
        User user = new User();
        BeanUtils.copyPropertiesIgnoreNull(userDTO, user);
        LocalDateTime now = LocalDateTime.now();
        String userId = UserUtil.getUserId();
        user.setUpdateTime(now);
        user.setUpdateUserId(userId);
        // 不能修改密码
        user.setPassword(null);
        this.updateById(user);

        // 删除用户角色
        userRoleRelService.remove(Wrappers.<UserRoleRel>update().lambda()
            .eq(UserRoleRel::getUserId, userDTO.getUserId()));
        // 新增用户角色
        List<UserRoleRel> userRoleRels = userDTO.getRole().stream().map(e -> {
            UserRoleRel userRoleRel = new UserRoleRel();
            userRoleRel.setRoleId(e);
            userRoleRel.setUserId(userDTO.getUserId());
            return userRoleRel;
        }).collect(Collectors.toList());
        userRoleRelService.saveBatch(userRoleRels);
    }

    /**
     * 修改本人信息（密码等）
     *
     * @param userDTO
     */
    @Override
    @CacheInvalidate(name= CacheConstants.LOGIN_USER)
    public void updateUserInfo(UserUpdateDTO userDTO) {
        User user = baseMapper.selectById(userDTO.getUserId());
        if (null == user) {
            log.warn("[修改个人信息] - 用户[userId={}, userName={}]不存在", userDTO.getUserId(), userDTO.getUserName());
            throw new SystemNoLogException(ResponseCode.USER_NOT_FIND);
        }

        User currentUser = UserUtil.loginUser().getUser();
        if (!StrUtil.equals(currentUser.getUserId(), userDTO.getUserId())) {
            log.warn("[修改个人信息] - 不是本人的操作，当前登录用户[userId={}, userName={}]，被操作用户[userId={}, userName={}]",
                currentUser.getUserId(), currentUser.getUserName(), userDTO.getUserId(), userDTO.getUserName());
            throw new SystemNoLogException(ResponseCode.NO_AUTHENTICATION);
        }

        if (StrUtil.isNotBlank(userDTO.getPassword()) && StrUtil.isNotBlank(userDTO.getNewPassword())) {
            if (StrUtil.equals(passwordEncoder.encode(userDTO.getPassword()), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                user.setPwdUpdateTime(LocalDateTime.now());
            } else {
                log.warn("[修改个人信息] - 用户[userId={}, userName={}]原密码错误，修改密码失败", userDTO.getUserId(), userDTO.getUserName());
                throw new SystemNoLogException(ResponseCode.USER_PASSWORD_ERROR);
            }
        }
        user.setAvatar(userDTO.getAvatar());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setNickName(userDTO.getNickName());
        user.setUpdateUserId(userDTO.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(user);
    }
}
