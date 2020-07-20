package xyz.gits.boot.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.gits.boot.api.system.dto.UserAddDTO;
import xyz.gits.boot.api.system.enums.Status;
import xyz.gits.boot.system.entity.Resource;
import xyz.gits.boot.system.entity.Role;
import xyz.gits.boot.system.entity.User;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.service.IResourceService;
import xyz.gits.boot.system.service.IRoleService;
import xyz.gits.boot.system.service.IUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author songyinyin
 * @date 2020/4/7 下午 10:51
 */
public class SystemServiceImpl implements SystemService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IResourceService resourceService;

    @Override
    public RestResponse<LoginUser<UserDetailsVO>> loadUserByUsername(String userName) {
        User user = userService.getByUsername(userName);
        if (ObjectUtil.isNull(user)) {
            return RestResponse.build(ResponseCode.USER_NOT_EXIST);
        }
        LoginUser<UserDetailsVO> loginUser = getLoginUser(user);
        return RestResponse.success(loginUser);
    }

    @Override
    public RestResponse<LoginUser<UserDetailsVO>> loadUserByBiz(String fieldName, String value) {
        if (StrUtil.isBlank(fieldName) || StrUtil.isBlank(value)) {
            throw new IllegalArgumentException("SystemServiceImpl#loadUserByBiz() fieldName 或者 value 不能为空");
        }
        User user = userService.getOne(Wrappers.<User>query().eq(fieldName, value));
        if (ObjectUtil.isNull(user)) {
            return RestResponse.build(ResponseCode.USER_NOT_EXIST);
        }
        LoginUser<UserDetailsVO> loginUser = getLoginUser(user);
        return RestResponse.success(loginUser);
    }

    @Override
    public RestResponse<LoginUser<UserDetailsVO>> registerUser(UserAddDTO user) {
        LoginUser<UserDetailsVO> loginUser = userService.saveUser(user);
        return RestResponse.success(loginUser);
    }

    private LoginUser<UserDetailsVO> getLoginUser(User user) {
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

}
