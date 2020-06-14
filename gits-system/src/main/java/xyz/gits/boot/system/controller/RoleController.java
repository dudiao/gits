package xyz.gits.boot.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.RoleResourceDTO;
import xyz.gits.boot.api.system.dto.RoleSaveDTO;
import xyz.gits.boot.api.system.dto.RoleUpdateDTO;
import xyz.gits.boot.api.system.entity.Role;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.response.TableResponse;
import xyz.gits.boot.system.service.IRoleResourceRelService;
import xyz.gits.boot.system.service.IRoleService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@RestController
@Api(value = "role", tags = "角色管理")
public class RoleController extends BasicController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleResourceRelService roleResourceRelService;

    @GetMapping("/system/role/page")
    @ApiOperation(value = "分页查询角色")
    public TableResponse<Role> page() {
        IPage<Role> page = roleService.getPage(null);
        return TableResponse.success(page.getTotal(), page.getRecords());
    }

    @GetMapping("/system/role/list")
    @ApiOperation(value = "角色列表")
    public RestResponse<List<Role>> list() {
        List<Role> list = roleService.list();
        return RestResponse.success(list);
    }

    @PostMapping("/system/role/save")
    @ApiOperation(value = "新增角色")
    public RestResponse save(@Valid @RequestBody RoleSaveDTO dto) {
        roleService.saveRole(dto);
        return RestResponse.success();
    }

    @PutMapping("/system/role/resource")
    @ApiOperation(value = "更新角色资源")
    public RestResponse resource(@Valid @RequestBody RoleResourceDTO dto) {
        roleResourceRelService.updateResource(dto);
        return RestResponse.success();
    }

    @PutMapping("/system/role/update")
    @ApiOperation(value = "修改角色")
    public RestResponse update(@Valid @RequestBody RoleUpdateDTO dto) {
        roleService.updateRole(dto);
        return RestResponse.success();
    }

    @DeleteMapping("/system/role/delete")
    @ApiOperation(value = "删除角色")
    public RestResponse delete(@NotBlank(message = "角色id不能为空") @ApiParam(name = "roleId", value = "角色id")
                               @RequestParam("roleId") String roleId) {
        roleService.removeById(roleId);
        return RestResponse.success();
    }

}

