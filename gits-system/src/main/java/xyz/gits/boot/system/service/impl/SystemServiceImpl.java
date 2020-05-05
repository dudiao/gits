package xyz.gits.boot.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.gits.boot.api.system.dto.UserDTO;
import xyz.gits.boot.api.system.entity.Resource;
import xyz.gits.boot.api.system.entity.Role;
import xyz.gits.boot.api.system.entity.User;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.UserVO;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.service.IResourceService;
import xyz.gits.boot.system.service.IRoleService;
import xyz.gits.boot.system.service.IUserService;

import java.sql.Struct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author songyinyin
 * @date 2020/4/7 下午 10:51
 */
public class SystemServiceImpl implements SystemService {

    @Autowired
    private IResourceService resourceService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Override
    public UserVO loadUserByUsername(String userName) {
        User user = userService.getByUsername(userName);
        if (ObjectUtil.isNull(user)) {
            return null;
        }
        return getUserVO(user);
    }

    @Override
    public UserVO loadUserByBiz(String fieldName, String value) {
        if (StrUtil.isBlank(fieldName) || StrUtil.isBlank(value)) {
            throw new IllegalArgumentException("SystemServiceImpl#loadUserByBiz() fieldName 或者 value 不能为空");
        }
        User user = userService.getOne(Wrappers.<User>query().eq(fieldName, value));
        if (ObjectUtil.isNull(user)) {
            return null;
        }
        return getUserVO(user);
    }

    @Override
    public UserVO registerUser(UserDTO user) {
        return userService.saveUser(user);
    }

    private UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyPropertiesIgnoreNull(user, userVO);

        // 权限
        List<Resource> resources = resourceService.selectListByUser(user.getUserId());
        Set<String> permissions = resources.stream().map(Resource::getPermission).collect(Collectors.toSet());
        userVO.setPermissions(permissions);

        // 角色
        Set<String> roles = roleService.getRolesByUserId(user.getUserId())
                .stream().map(Role::getRoleName).collect(Collectors.toSet());
        userVO.setRoles(roles);
        return userVO;
    }

}
