package xyz.gits.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import xyz.gits.boot.api.system.dto.UserDTO;
import xyz.gits.boot.api.system.entity.User;
import xyz.gits.boot.api.system.entity.UserRoleRel;
import xyz.gits.boot.auth.UserUtil;
import xyz.gits.boot.common.constants.SystemConstants;
import xyz.gits.boot.common.response.ResponseCode;
import xyz.gits.boot.common.response.RestResponse;
import xyz.gits.boot.common.utils.BeanUtils;
import xyz.gits.boot.system.mapper.UserMapper;
import xyz.gits.boot.system.service.IUserRoleRelService;
import xyz.gits.boot.system.service.IUserService;
import xyz.gits.boot.common.basic.BasicServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
@Service
public class UserServiceImpl extends BasicServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRoleRelService userRoleRelService;

    @Override
    public IPage<User> getPage() {
        QueryWrapper<User> queryWrapper = parseParameter();
        return page(queryWrapper);
    }

    @Override
    public User getByUsername(String userName) {
        return baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, userName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyPropertiesIgnoreNull(userDTO, user);

        user.setPwdLockFlag(SystemConstants.PWD_UNLOCK);
        user.setStopFlag(SystemConstants.USER_ENABLE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        baseMapper.insert(user);

        // 保存用户的角色
        List<UserRoleRel> userRoleList = userDTO.getRole()
                .stream().map(roleId -> {
                    UserRoleRel userRole = new UserRoleRel();
                    userRole.setUserId(user.getUserId());
                    userRole.setRoleId(roleId);
                    return userRole;
                }).collect(Collectors.toList());
        userRoleRelService.saveBatch(userRoleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyPropertiesIgnoreNull(userDTO, user);
        LocalDateTime now = LocalDateTime.now();
        String userId = UserUtil.loginUser().getUser().getUserId();
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

    @Override
    public RestResponse updateUserInfo(UserDTO userDTO) {
        User user = baseMapper.selectById(userDTO.getUserId());
        if (null == user) {
            log.warn("[修改个人信息] - 用户[userId={}, userName={}]不存在", userDTO.getUserId(), userDTO.getUserName());
            return RestResponse.build(ResponseCode.USER_NOT_FIND);
        }

        User currentUser = UserUtil.loginUser().getUser();
        if (!StrUtil.equals(currentUser.getUserId(), userDTO.getUserId())) {
            log.warn("[修改个人信息] - 不是本人的操作，当前登录用户[userId={}, userName={}]，被操作用户[userId={}, userName={}]",
                    currentUser.getUserId(), currentUser.getUserName(), userDTO.getUserId(), userDTO.getUserName());
            return RestResponse.build(ResponseCode.NO_AUTHENTICATION);
        }

        if (StrUtil.isNotBlank(userDTO.getPassword()) && StrUtil.isNotBlank(userDTO.getNewPassword())) {
            if (StrUtil.equals(passwordEncoder.encode(userDTO.getPassword()), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                user.setPwdUpdateTime(LocalDateTime.now());
            } else {
                log.warn("[修改个人信息] - 用户[userId={}, userName={}]原密码错误，修改密码失败", userDTO.getUserId(), userDTO.getUserName());
                return RestResponse.build(ResponseCode.USER_PASSWORD_ERROR);
            }
        }
        user.setAvatar(userDTO.getAvatar());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setNickName(userDTO.getNickName());
        user.setUpdateUserId(userDTO.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(user);
        return RestResponse.success();
    }
}
