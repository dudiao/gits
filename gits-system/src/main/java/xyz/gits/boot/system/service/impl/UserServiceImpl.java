package xyz.gits.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import xyz.gits.boot.api.system.dto.UserAddDTO;
import xyz.gits.boot.api.system.enums.*;
import xyz.gits.boot.api.system.utils.ConfigUtil;
import xyz.gits.boot.api.system.utils.AuthUtils;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.constants.ConfigConstants;
import xyz.gits.boot.common.core.exception.SystemException;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.dto.user.UserQueryDTO;
import xyz.gits.boot.system.dto.user.UserUpdateDTO;
import xyz.gits.boot.system.entity.*;
import xyz.gits.boot.system.mapper.OrgMapper;
import xyz.gits.boot.system.mapper.UserMapper;
import xyz.gits.boot.system.service.IResourceService;
import xyz.gits.boot.system.service.IRoleService;
import xyz.gits.boot.system.service.IUserRoleRelService;
import xyz.gits.boot.system.service.IUserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private OrgMapper orgMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRoleRelService userRoleRelService;

    /**
     * 分页查询
     *
     * @return 用户详情 {@link IPage<User>}
     * @author null
     * @date 2020/5/26 17:38
     */
    @Override
    public IPage<User> getPage(UserQueryDTO userQueryDTO) {
        QueryWrapper<User> queryWrapper = parseParameter();
        queryWrapper.lambda()
            .like(StrUtil.isNotEmpty(userQueryDTO.getPwdLockStatusCode()), User::getPwdLockStatus, userQueryDTO.getPwdLockStatusCode())
            .like(StrUtil.isNotEmpty(userQueryDTO.getStopStatusCode()), User::getStopStatus, userQueryDTO.getStopStatusCode())
            .eq(StrUtil.isNotEmpty(userQueryDTO.getNickName()), User::getNickName, userQueryDTO.getNickName())
            .eq(StrUtil.isNotEmpty(userQueryDTO.getOrgId()), User::getOrgId, userQueryDTO.getOrgId())
            .eq(StrUtil.isNotEmpty(userQueryDTO.getUserName()), User::getUserName, userQueryDTO.getUserName());
        return page(queryWrapper).setCurrent(userQueryDTO.getCurrentPage()).setSize(userQueryDTO.getPageSize());
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名（登录名）
     * @return 前端展示用户详情{@link User}
     * @author null
     * @date 2020/5/26 17:38
     */
    @Override
    public LoginUser<UserDetailsVO> getByUsername(String userName) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUserName, userName));

        LoginUser<UserDetailsVO> loginUser = new LoginUser<>();
        UserDetailsVO userDetailsVO = new UserDetailsVO();
        BeanUtils.copyPropertiesIgnoreNull(user, userDetailsVO);
        loginUser.setUser(userDetailsVO);

        // 角色 Role::getRoleId
        Set<String> roleIds = roleService.getRolesByUserId(user.getUserId(), Status.VALID)
            .stream().map(Role::getRoleId).collect(Collectors.toSet());
        loginUser.setRoles(roleIds);

        // 权限 Resource::getPermission
        Set<String> permissions = new HashSet<>();
        roleIds.forEach(roleId -> {
            List<String> permissionList = resourceService.findResourceByRoleId(roleId)
                .stream()
                .filter(resource -> StrUtil.isNotEmpty(resource.getPermission()))
                .map(Resource::getPermission)
                .collect(Collectors.toList());
            permissions.addAll(permissionList);
        });
        loginUser.setPermissions(permissions);

        // 密码
        loginUser.setPassword(user.getPassword());
        return loginUser;
    }

    /**
     * 新增用户
     *
     * @param dto 用户DTO
     * @return 前端用户展示信息 1、若干密码为空就从配置中心读取默认密码{@link UserDetailsVO}
     * @author null
     * @date 2020/5/26 17:39
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginUser<UserDetailsVO> saveUser(UserAddDTO dto) {

        Org org = orgMapper.selectById(dto.getOrgId());
        if (BeanUtil.isEmpty(org)) {
            log.warn("[用户管理-修改用户]-机构不存在，机构id:orgId{}", dto.getOrgId());
            throw new SystemNoLogException(ResponseCode.ORG_NOT_EXIST);
        }

        User user = new User();
        user.setPwdLockStatus(LockStatus.UN_LOCK);
        user.setStopStatus(StopStatus.fromString(dto.getStopStatusCode()));
        user.setSex(Sex.fromString(dto.getSexCode()));
        user.setCreateUserId(AuthUtils.getUserId());
        user.setCreateTime(LocalDateTime.now());
        user.setSex(Sex.fromString(dto.getSexCode()));
        if (StrUtil.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // 默认密码需要从配置中心读取
            String defaultPassword = ConfigUtil.getString(ConfigConstants.SYSTEM_PASSWORD_INIT);
            user.setPassword(passwordEncoder.encode(defaultPassword));
        }
        BeanUtils.copyPropertiesIgnoreNull(dto, user);
        baseMapper.insert(user);

        LoginUser<UserDetailsVO> loginUser = new LoginUser<>();
        UserDetailsVO userDetailsVO = new UserDetailsVO();
        BeanUtils.copyPropertiesIgnoreNull(user, userDetailsVO);
        loginUser.setUser(userDetailsVO);
        loginUser.setPassword(user.getPassword());

        // 保存用户的角色
        if (CollUtil.isEmpty(dto.getRole())) {
            return loginUser;
        }
        // 保存用户角色的关系
        List<UserRoleRel> userRoleList = dto.getRole()
            .stream().map(roleId -> {
                UserRoleRel userRole = new UserRoleRel();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(roleId);
                return userRole;
            }).collect(Collectors.toList());
        userRoleRelService.saveBatch(userRoleList);
        loginUser.setRoles(new HashSet<>(dto.getRole()));

        return loginUser;
    }

    /**
     * 修改用户
     *
     * @param userUpdateDTO 用户信息传输DTO
     * @author null
     * @date 2020/5/26 17:39
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateDTO userUpdateDTO) {

        Org org = orgMapper.selectById(userUpdateDTO.getOrgId());
        if (BeanUtil.isEmpty(org)) {
            log.warn("[用户管理-修改用户]-机构不存在，机构id:orgId{}", userUpdateDTO.getOrgId());
            throw new SystemNoLogException(ResponseCode.ORG_NOT_EXIST);
        }

        User user = baseMapper.selectById(userUpdateDTO.getUserId());
        if (null == user) {
            log.warn("[用户管理-修改个人信息]-用户不存在，用户id:userId={}, 用户名:userName={}", userUpdateDTO.getUserId(), userUpdateDTO.getUserName());
            throw new SystemNoLogException(ResponseCode.USER_NOT_EXIST);
        }
        BeanUtils.copyPropertiesIgnoreNull(userUpdateDTO, user);
        LocalDateTime now = LocalDateTime.now();
        String userId = AuthUtils.getUserId();
        user.setUpdateTime(now);
        user.setUpdateUserId(userId);
        user.setStopStatus(StopStatus.fromString(userUpdateDTO.getStopStatusCode()));
        user.setSex(Sex.fromString(userUpdateDTO.getSexCode()));
        // 不能修改密码
        user.setPassword(null);
        this.updateById(user);
        /*// 删除用户角色
        userRoleRelService.remove(Wrappers.<UserRoleRel>update().lambda()
                .eq(UserRoleRel::getUserId, userDTO.getUserId()));
        // 新增用户角色
        List<UserRoleRel> userRoleRels = userDTO.getRole().stream().map(e -> {
            UserRoleRel userRoleRel = new UserRoleRel();
            userRoleRel.setRoleId(e);
            userRoleRel.setUserId(userDTO.getUserId());
            return userRoleRel;
        }).collect(Collectors.toList());*/
        //serRoleRelService.saveBatch(user);
    }

    /**
     * 修改用户
     *
     * @param userUpdateDTO 用户信息传输DTO
     * @author null
     * @date 2020/5/26 17:39
     */
    @Override
    public void updateUserInfo(UserUpdateDTO userUpdateDTO) {
        User user = baseMapper.selectById(userUpdateDTO.getUserId());
        if (null == user) {
            log.warn("[用户管理-修改个人信息]-用户不存在，用户id:userId={}, 用户名:userName={}", userUpdateDTO.getUserId(), userUpdateDTO.getUserName());
            throw new SystemNoLogException(ResponseCode.USER_NOT_EXIST);
        }

        LoginUser loginUser = (LoginUser) AuthUtils.loginUser();
        UserDetailsVO currentUser = loginUser.getUser();
        if (!StrUtil.equals(currentUser.getUserId(), userUpdateDTO.getUserId())) {
            log.warn("[用户管理-修改个人信息]-不是本人的操作，当前登录用户id:userId={}，用户名:userName={}，被操作用户id:userId={}, 用户名:userName={}",
                currentUser.getUserId(), currentUser.getUserName(), userUpdateDTO.getUserId(), userUpdateDTO.getUserName());
            throw new SystemException(ResponseCode.NO_AUTHENTICATION);
        }
        //修改密码
        if (StrUtil.isNotBlank(userUpdateDTO.getPassword()) && StrUtil.isNotBlank(userUpdateDTO.getNewPassword())) {
            if (StrUtil.equals(passwordEncoder.encode(userUpdateDTO.getPassword()), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
                user.setPwdUpdateTime(LocalDateTime.now());
            } else {
                log.warn("[用户管理-修改个人信息]-用户原密码错误，修改密码失败，用户id:userId={}, 用户名userName={}", userUpdateDTO.getUserId(), userUpdateDTO.getUserName());
                throw new SystemException(ResponseCode.USER_PASSWORD_ERROR);
            }
        }
        user.setAvatar(userUpdateDTO.getAvatar());
        user.setEmail(userUpdateDTO.getEmail());
        user.setMobile(userUpdateDTO.getMobile());
        user.setNickName(userUpdateDTO.getNickName());
        user.setUpdateUserId(userUpdateDTO.getUserId());
        user.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(user);
    }

    /**
     * 删除用户
     *
     * @param userIds 用户ID集合
     * @author null
     * @date 2020/5/26 17:39
     */
    @Override
    public void delete(List<String> userIds) {
        userMapper.deleteBatchIds(userIds);
    }

    /**
     * 修改启用停用标志
     *
     * @param userId   用户ID
     * @param stopFlag 启用停用标志[0:启用;1:停用]
     * @author null
     * @date 2020/5/26 17:39
     */
    @Override
    public void updateUserStatus(String userId, String stopFlag) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.warn("[用户管理-修改个人状态]-用户不存在不可以修改状态，用户名:UserId={}", user.getUserId());
            throw new SystemNoLogException(ResponseCode.USER_NOT_EXIST);
        }
        if (user.isAdmin()) {
            log.warn("[用户管理-修改个人状态]-用户是超级管理员不可以修改状态，用户名:UserName={}", user.getUserName());
            throw new SystemNoLogException(ResponseCode.UPDATE_USER_STATUS);
        }
        user.setStopStatus(StopStatus.fromString(stopFlag));
        userMapper.updateById(user);
    }

    /**
     * 修改密码锁定标志
     *
     * @param userId      用户ID
     * @param pwdLockFlag 密码锁定标志[0-未锁定,1-密码错误锁定]
     * @author null
     * @date 2020/5/26 17:39
     */
    @Override
    public void updateUserLock(String userId, String pwdLockFlag) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.warn("[用户管理-修改个人状态]-用户名不存在不可以修改状态，用户id:UserId={}", user.getUserId());
            throw new SystemNoLogException(ResponseCode.USER_NOT_EXIST);
        }
        if (user.isAdmin()) {
            log.warn("[用户管理-修改个人状态]-用户名是超级管理员不可以修改状态，用户名UserName={}", user.getUserName());
            throw new SystemNoLogException(ResponseCode.UPDATE_USER_STATUS);
        }
        user.setPwdLockStatus(LockStatus.fromString(pwdLockFlag));
        userMapper.updateById(user);
    }

    /**
     * 重置密码
     *
     * @param user 用户信息
     *             默认密码      111111-a
     * @author null
     * @date 2020/5/26 17:39
     */
    @Override
    public void userPasswordReset(User user) {
        if (user.isAdmin()) {
            log.warn("[用户管理-重置密码]-当前用户是超级管理员密码不可以被重置，用户名:UserName={}", user.getUserName());
            throw new SystemNoLogException(ResponseCode.UPDATE_USER_PASSWORD);
        }
        // 密码加密
        String passwordInit = ConfigConstants.SYSTEM_PASSWORD_INIT;
        user.setPassword(passwordEncoder.encode(passwordInit));
        user.setUpdateTime(LocalDateTime.now());
        user.setPwdUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }


    /**
     * 获取该机构下用户
     *
     * @param orgId 机构id
     * @return 用户集合 {@link List<User>}
     * @author null
     * @date 2020/5/29 14:44
     */
    @Override
    public List<User> findUserByOrgId(String orgId) {
        // TODO: 2020/5/29
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.lambda().eq(User::getOrgId, orgId);
        return userMapper.selectList(qw);
    }

    /**
     * 根据用户id查询用户信息 缓存中有先冲缓存中获取否则从库走获取再缓存信息
     *
     * @param userId 用户id
     * @return 用户信息 {@link User}
     * @author null
     * @date 2020/5/29 17:48
     */
    @Override
    public User getUserById(String userId) {
        // TODO: 2020/5/29 缓存
        return userMapper.selectById(userId);
    }

/*
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginUser<UserDetailsVO> saveUser(UserAddDTO dto) {
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

        LoginUser<UserDetailsVO> loginUser = new LoginUser<>();
        UserDetailsVO userDetailsVO = new UserDetailsVO();
        BeanUtils.copyPropertiesIgnoreNull(user, userDetailsVO);
        loginUser.setUser(userDetailsVO);
        loginUser.setPassword(user.getPassword());

        // 保存用户的角色
        if (CollUtil.isEmpty(dto.getRole())) {
            return loginUser;
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
        loginUser.setRoles(new HashSet<>(dto.getRole()));
        return loginUser;
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

    *//**
     * 修改本人信息（密码等）
     *
     * @param userDTO
     *//*
    @Override
    @CacheInvalidate(name= CacheConstants.LOGIN_USER)
    public void updateUserInfo(UserUpdateDTO userDTO) {
        User user = baseMapper.selectById(userDTO.getUserId());
        if (null == user) {
            log.warn("[修改个人信息] - 用户[userId={}, userName={}]不存在", userDTO.getUserId(), userDTO.getUserName());
            throw new SystemNoLogException(ResponseCode.USER_NOT_EXIST);
        }

        if (!StrUtil.equals(UserUtil.getUserId(), userDTO.getUserId())) {
            log.warn("[修改个人信息] - 不是本人的操作，当前登录用户[userId={}, userName={}]，被操作用户[userId={}, userName={}]",
                UserUtil.getUserId(), UserUtil.getUserName(), userDTO.getUserId(), userDTO.getUserName());
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
    }*/
}
