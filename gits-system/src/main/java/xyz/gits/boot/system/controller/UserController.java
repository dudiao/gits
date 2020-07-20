package xyz.gits.boot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.UserAddDTO;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.response.TableResponse;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.dto.user.UserRoleDTO;
import xyz.gits.boot.system.dto.user.UserUpdateDTO;
import xyz.gits.boot.system.entity.User;
import xyz.gits.boot.system.service.IUserRoleRelService;
import xyz.gits.boot.system.service.IUserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@RestController
@Api(value = "user", tags = "用户管理")
public class UserController extends BasicController {

    @Autowired
    protected IUserService userService;
    @Autowired
    protected SystemService systemService;
    @Autowired
    protected IUserRoleRelService userRoleRelService;

    @GetMapping("/system/user/page")
    @ApiOperation(value = "分页查询用户")
    public TableResponse<UserDetailsVO> page() {
        IPage<User> page = userService.getPage(null);

        List<UserDetailsVO> userDetailsVOList = page.getRecords().stream().map(e -> {
            UserDetailsVO userDetailsVO = new UserDetailsVO();
            BeanUtils.copyPropertiesIgnoreNull(e, userDetailsVO);
            return userDetailsVO;
        }).collect(Collectors.toList());

        return TableResponse.success(page.getTotal(), userDetailsVOList);
    }

    @PostMapping("/system/user")
    @ApiOperation(value = "新增用户")
    @PreAuthorize("@ps.permission('system:user:add')")
    public RestResponse save(@Validated @RequestBody UserAddDTO dto) {
        userService.saveUser(dto);
        return RestResponse.success();
    }

    @PutMapping("/system/user")
    @ApiOperation(value = "更新用户")
    @PreAuthorize("@ps.permission('system:user:update')")
    public RestResponse updateUser(@Validated @RequestBody UserUpdateDTO userDTO) {
        userService.updateUser(userDTO);
        return RestResponse.success();
    }

    @PutMapping("/system/user/edit")
    @ApiOperation(value = "修改个人信息（包括密码等）")
    public RestResponse updateUserInfo(@Validated @RequestBody UserUpdateDTO userDTO) {
        userService.updateUserInfo(userDTO);
        return RestResponse.success();
    }

    @GetMapping("/system/user/{userName}")
    @ApiOperation(value = "查看用户详情")
    public RestResponse<LoginUser<UserDetailsVO>> detail(@ApiParam(name = "userName", value = "用户名") @PathVariable("userName") String userName) {
        RestResponse<LoginUser<UserDetailsVO>> response = systemService.loadUserByUsername(userName);
        // 不返回密码
        response.getData().setPassword(null);
        return response;
    }

    @GetMapping("/system/user/status")
    @ApiOperation("修改启用停用标志")
    public RestResponse updateUserStatus(@ApiParam(name = "userId", value = "用户Id", required = true) @RequestParam("userId") String userId,
                                         @ApiParam(name = "stopStatusCode", value = "启用停用标志[0:启用;1:停用]", required = true) @RequestParam("stopStatusCode") String stopStatusCode) {

        userService.updateUserStatus(userId, stopStatusCode);
        return RestResponse.success();
    }

    @GetMapping("/system/user/lock")
    @ApiOperation("修改密码锁定标志（0-未锁定，1-密码错误锁定）")
    public RestResponse updateUserLock(@ApiParam(name = "userId", value = "用户Id", required = true) @RequestParam("userId") String userId,
                                       @ApiParam(name = "pwdLockStatusCode", value = "密码锁定标志[0-未锁定，1-密码错误锁定]", required = true) @RequestParam("pwdLockStatusCode") String pwdLockStatusCode) {

        userService.updateUserLock(userId, pwdLockStatusCode);
        return RestResponse.success();
    }

    @GetMapping("/system/user/reset")
    @ApiOperation("密码重置")
    public RestResponse userPasswordReset(@ApiParam(name = "userId", value = "用户Id", required = true) @RequestParam("userId") String userId) {
        //查询用户信息
        User user = userService.getById(userId);
        userService.userPasswordReset(user);
        return RestResponse.success();
    }

    @PostMapping("/system/user/authorize")
    @ApiOperation("添加用户角色信息")
    public RestResponse authorize(@Validated @RequestBody UserRoleDTO userRoleDTO) {
        userRoleRelService.authorize(userRoleDTO);
        return RestResponse.success();
    }

}

