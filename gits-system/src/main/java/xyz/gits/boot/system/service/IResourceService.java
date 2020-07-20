package xyz.gits.boot.system.service;

import xyz.gits.boot.system.vo.resource.ResourceTree;
import xyz.gits.boot.common.core.basic.BasicService;
import xyz.gits.boot.system.dto.resource.ResourceDTO;
import xyz.gits.boot.system.entity.Resource;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
     * 获取角色关联的资源列表(状态为显示)
     *
     * @param roleId 角色id
     * @return {@link List<Resource>}
     * @author null
     * @date 2020/7/20 10:25
     */
    List<Resource> findResourceByRoleId(String roleId);

    /**
     * 删除资源
     *
     * @param resourceId 资源id
     */
    void deleteResource(String resourceId);

    /**
     * 构建资源树
     *
     * @param list
     * @return
     */
    List<ResourceTree> buildTree(Collection<Resource> list);


    /**
     * 修改资源信息
     *
     * @param resourceDTO 修改菜单信息DTO
     * @author null
     * @date 2020/5/25 17:31
     */
    void updateResource(ResourceDTO resourceDTO);


    /**
     * 新增资源信息
     *
     * @param resourceDTO 新增菜单传输信息
     * @author null
     * @date 2020/5/25 17:32
     */
    void saveResource(ResourceDTO resourceDTO);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return {@link Set < String>}
     * @author null
     * @date 2020/5/26 17:13
     */
    Set<String> selectResourcePermsByUserId(String userId);
}
