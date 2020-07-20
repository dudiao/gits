package xyz.gits.boot.system.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.gits.boot.api.system.enums.VisibleStatus;
import xyz.gits.boot.api.system.utils.AuthUtils;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.entity.Resource;
import xyz.gits.boot.system.service.IResourceService;
import xyz.gits.boot.system.vo.resource.ResourceTree;
import xyz.gits.boot.system.vo.resource.ResourceVO;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (AuthUtils.isAdmin()) {
            List<Resource> list = resourceService.list(Wrappers.<Resource>lambdaQuery().eq(Resource::getVisible, VisibleStatus.SHOW));
            return RestResponse.success(resourceService.buildTree(list));
        }

        Set<Resource> resourceSet = new HashSet<>();
        AuthUtils.getUserRoles().forEach(roleId -> resourceSet.addAll(resourceService.findResourceByRoleId(roleId)));
        return RestResponse.success(resourceService.buildTree(resourceSet));
    }

    @GetMapping("/system/resource/tree")
    @ApiOperation("完整资源树")
    public RestResponse<List<ResourceTree>> tree() {
        List<Resource> list = resourceService.list();
        return RestResponse.success(resourceService.buildTree(list));
    }

    @GetMapping("/system/resource/role")
    @ApiOperation(value = "角色的资源集合")
    public RestResponse<List<ResourceVO>> getUserResource(@ApiParam(name = "roleId", value = "角色id", required = true) @RequestParam("roleId") String roleId) {
        List<Resource> list = resourceService.findResourceByRoleId(roleId);
        List<ResourceVO> resourceVOList = list.stream().map(resource -> {
            ResourceVO resourceVO = new ResourceVO();
            BeanUtils.copyPropertiesIgnoreNull(resource, resourceVO);
            return resourceVO;
        }).collect(Collectors.toList());
        return RestResponse.success(resourceVOList);
    }

    @DeleteMapping("/system/resource/delete")
    @ApiOperation(value = "删除资源")
    public RestResponse deleteResource(@NotBlank(message = "资源id不能为空") @ApiParam(name = "resourceId", value = "资源id")
                                       @RequestParam("resourceId") String resourceId) {
        resourceService.deleteResource(resourceId);
        return RestResponse.success();
    }

}

