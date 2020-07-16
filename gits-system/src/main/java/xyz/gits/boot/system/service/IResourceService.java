package xyz.gits.boot.system.service;

import xyz.gits.boot.api.system.dto.ResourceDTO;
import xyz.gits.boot.system.entity.Resource;
import xyz.gits.boot.api.system.vo.ResourceTree;
import xyz.gits.boot.common.core.basic.BasicService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface IResourceService extends BasicService<Resource> {

    /**
     * 通过角色id查询资源
     *
     * @param roleId 角色id
     * @return
     */
    List<Resource> findResourceByRoleId(String roleId);

    void saveResource(ResourceDTO dto);

    /**
     * 删除资源
     *
     * @param resourceId 资源id
     */
    void deleteResource(String resourceId);

    void updateResource(ResourceDTO dto);

    /**
     * 构建资源树
     *
     * @param list
     * @return
     */
    List<ResourceTree> buildTree(Collection<Resource> list);
}
