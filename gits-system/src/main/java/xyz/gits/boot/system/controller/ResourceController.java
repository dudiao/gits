package xyz.gits.boot.system.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.ResourceDTO;
import xyz.gits.boot.api.system.entity.Resource;
import xyz.gits.boot.api.system.enums.VisibleType;
import xyz.gits.boot.api.system.utils.UserUtil;
import xyz.gits.boot.api.system.vo.ResourceTree;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.system.service.IResourceService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@RestController
@Api(value = "resource", tags = "资源管理")
public class ResourceController extends BasicController {

    @Autowired
    private IResourceService resourceService;

    @GetMapping("/system/resource/tree/current")
    @ApiOperation(value = "获取当前用户的树形资源集合，用户登录后或者刷新页面时访问")
    public RestResponse<List<ResourceTree>> getUserResource() {

        // 如果是管理员，显示所有菜单
        if (UserUtil.isAdmin()) {
            List<Resource> list = resourceService.list(Wrappers.<Resource>lambdaQuery().eq(Resource::getVisible, VisibleType.SHOW));
            return RestResponse.success(resourceService.buildTree(list));
        }

        Set<Resource> resourceSet = new HashSet<>();
        UserUtil.getUserRoles().forEach(roleId -> resourceSet.addAll(resourceService.findResourceByRoleId(roleId)));
        return RestResponse.success(resourceService.buildTree(resourceSet));
    }

    @GetMapping("/system/resource/tree")
    @ApiOperation("完整菜单树")
    public RestResponse<List<ResourceTree>> tree() {
        List<Resource> list = resourceService.list();
        return RestResponse.success(resourceService.buildTree(list));
    }

    @GetMapping("/system/resource/tree/role")
    @ApiOperation(value = "角色的资源集合")
    public RestResponse<List<Resource>> getUserResource(@NotBlank(message = "角色id不能为空") @ApiParam(name = "roleId", value = "角色id")
                                                        @RequestParam("roleId") String roleId) {
        List<Resource> list = resourceService.findResourceByRoleId(roleId);
        return RestResponse.success(list);
    }

    @PostMapping("/system/resource/save")
    @ApiOperation(value = "新增资源")
    public RestResponse save(@Valid @RequestBody ResourceDTO dto) {
        resourceService.saveResource(dto);
        return RestResponse.success();
    }

    @DeleteMapping("/system/resource/delete")
    @ApiOperation(value = "删除资源")
    public RestResponse save(@NotBlank(message = "资源id不能为空") @ApiParam(name = "resourceId", value = "资源id")
                             @RequestParam("resourceId") String resourceId) {
        resourceService.deleteResource(resourceId);
        return RestResponse.success();
    }

    @PutMapping("/system/resource/update")
    @ApiOperation(value = "更新资源")
    public RestResponse update(@Valid @RequestBody ResourceDTO dto) {
        resourceService.updateResource(dto);
        return RestResponse.success();
    }

}

