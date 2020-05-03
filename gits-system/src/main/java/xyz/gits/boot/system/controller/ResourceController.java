package xyz.gits.boot.system.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.system.service.IResourceService;

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

    @GetMapping("resource")
    @ApiOperation(value = "获取当前用户的树形菜单集合")
    public RestResponse getUserResource(String type, Integer parentId) {
        return null;
    }

}

