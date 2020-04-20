package xyz.gits.boot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.UserDTO;
import xyz.gits.boot.api.system.entity.User;
import xyz.gits.boot.api.system.service.SystemService;
import xyz.gits.boot.api.system.vo.UserVO;
import xyz.gits.boot.common.basic.BasicController;
import xyz.gits.boot.common.response.RestResponse;
import xyz.gits.boot.common.response.TableResponse;
import xyz.gits.boot.common.utils.BeanUtils;
import xyz.gits.boot.common.validate.CreateGroup;
import xyz.gits.boot.common.validate.UpdateGroup;
import xyz.gits.boot.system.service.IUserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@RestController
@RequestMapping("/system/user")
public class UserController extends BasicController {

    @Autowired
    private IUserService userService;
    @Autowired
    private SystemService systemService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询用户")
    @PreAuthorize("@ps.permission('system:user:page')")
    public TableResponse<UserVO> page() {
        IPage<User> page = userService.getPage();

        List<UserVO> userVOList = page.getRecords().stream().map(e -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyPropertiesIgnoreNull(e, userVO);
            return userVO;
        }).collect(Collectors.toList());

        return TableResponse.success(page.getTotal(), userVOList);
    }

    @PostMapping
    @ApiOperation(value = "新增用户")
    @PreAuthorize("@ps.permission('system:user:add')")
    public RestResponse save(@Validated(CreateGroup.class) @RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return RestResponse.success();
    }

    @PutMapping
    @ApiOperation(value = "更新用户")
    @PreAuthorize("@ps.permission('system:user:update')")
    public RestResponse updateUser(@Validated(UpdateGroup.class) @RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return RestResponse.success();
    }

    @PutMapping("/edit")
    @ApiOperation(value = "修改个人信息（包括密码等）")
    public RestResponse updateUserInfo(@Validated(UpdateGroup.class) @RequestBody UserDTO userDTO) {
        return userService.updateUserInfo(userDTO);
    }

    @GetMapping("/{userName}")
    @ApiOperation(value = "查看用户详情")
    @PreAuthorize("@ps.permission('system:user:detail')")
    public RestResponse<UserVO> detail(@ApiParam(name = "userName", value = "用户名") @RequestParam("userName") @PathVariable String userName) {
        UserVO userVO = systemService.loadUserByUsername(userName);
        return RestResponse.success(userVO);
    }

}

