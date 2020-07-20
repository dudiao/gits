package xyz.gits.boot.system.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.enums.Status;
import xyz.gits.boot.api.system.utils.ConvertUtils;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.response.TableResponse;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.dto.role.RoleQueryDTO;
import xyz.gits.boot.system.dto.role.RoleResourceDTO;
import xyz.gits.boot.system.dto.role.RoleSaveDTO;
import xyz.gits.boot.system.dto.role.RoleUpdateDTO;
import xyz.gits.boot.system.entity.Org;
import xyz.gits.boot.system.entity.Role;
import xyz.gits.boot.system.service.IOrgService;
import xyz.gits.boot.system.service.IRoleResourceRelService;
import xyz.gits.boot.system.service.IRoleService;
import xyz.gits.boot.system.vo.role.RoleDetailVO;
import xyz.gits.boot.system.vo.role.RoleListVO;
import xyz.gits.boot.system.vo.role.RoleVO;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private IOrgService orgService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleResourceRelService roleResourceRelService;

    @GetMapping("/system/role/page")
    @ApiOperation(value = "分页查询角色")
    public TableResponse<RoleDetailVO> page(@Validated RoleQueryDTO roleQueryDTO) {
        IPage<Role> page = roleService.getPage(roleQueryDTO);
        Map<String, Org> orgMap = orgService.getOrgMap(null);
        List<RoleDetailVO> roleDetailVOList = page.getRecords().stream().map(role -> {
            RoleDetailVO roleDetailVO = new RoleDetailVO();
            BeanUtils.copyPropertiesIgnoreNull(role, roleDetailVO);
            Org org = orgMap.get(role.getCreateOrgId());
            if (BeanUtil.isNotEmpty(org)) {
                roleDetailVO.setCreateOrgName(org.getOrgName());
            }
            return roleDetailVO;
        }).collect(Collectors.toList());

        ConvertUtils.convertUserList(roleDetailVOList);
        return TableResponse.success(page.getTotal(), roleDetailVOList);
    }

    @GetMapping("/system/role/list")
    @ApiOperation(value = "角色列表")
    public RestResponse<List<RoleVO>> list() {
        List<Role> list = roleService.list();
        List<RoleVO> roleVOList = list.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyPropertiesIgnoreNull(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
        return RestResponse.success(roleVOList);
    }

    @GetMapping("/system/role/user-role")
    @ApiOperation("根据用户id查询角色信息")
    public RestResponse<RoleListVO> findRoleByUserId(@ApiParam(name = "userId", value = "用户Id", required = true) @RequestParam("userId") String userId) {
        //用户角色信息
        List<Role> userRoleList = roleService.getRolesByUserId(userId, Status.VALID);
        List<RoleVO> userRoleVOList = userRoleList.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyPropertiesIgnoreNull(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
        //所有角色信息
        List<Role> allRoleList = roleService.getAllRoleByStatus(null);
        List<RoleVO> allRoleVOList = allRoleList.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyPropertiesIgnoreNull(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
        RoleListVO roleListVO = new RoleListVO(userRoleVOList, allRoleVOList);
        return RestResponse.success(roleListVO);
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

