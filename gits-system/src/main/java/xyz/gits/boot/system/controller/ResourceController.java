package xyz.gits.boot.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.gits.boot.api.system.entity.Resource;
import xyz.gits.boot.api.system.vo.ResourceTree;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.constants.SystemConstants;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.common.core.utils.TreeUtil;
import xyz.gits.boot.common.security.UserUtil;
import xyz.gits.boot.system.service.IResourceService;

import java.util.Comparator;
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

    @GetMapping("/system/resource/current/tree")
    @ApiOperation(value = "获取当前用户的树形菜单集合")
    public RestResponse<List<ResourceTree>> getUserResource() {
        Set<Resource> resourceSet = new HashSet<>();

        UserUtil.getUserRoles().forEach(roleId -> resourceSet.addAll(resourceService.findResourceByRoleId(roleId)));
        List<ResourceTree> resourceTreeList = resourceSet.stream().map(resource -> {
            ResourceTree tree = new ResourceTree();
            BeanUtils.copyPropertiesIgnoreNull(resource, tree);
            tree.setId(resource.getResourceId());
            tree.setParentId(resource.getParentResourceId());
            return tree;
        }).sorted(Comparator.comparingInt(ResourceTree::getOrderNum)).collect(Collectors.toList());
        return RestResponse.success(TreeUtil.bulid(resourceTreeList, SystemConstants.RESOURCE_ROOT_ID));
    }

}

