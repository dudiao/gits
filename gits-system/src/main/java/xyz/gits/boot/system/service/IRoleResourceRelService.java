package xyz.gits.boot.system.service;

import xyz.gits.boot.system.dto.role.RoleResourceDTO;
import xyz.gits.boot.system.entity.RoleResourceRel;
import xyz.gits.boot.common.core.basic.BasicService;

/**
 * <p>
 * 角色资源关联表 服务类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface IRoleResourceRelService extends BasicService<RoleResourceRel> {

    /**
     * 给角色授权菜单
     *
     * @param dto 角色菜单添加DTO{@link RoleResourceDTO}
     * @author null
     * @date 2020/5/26 17:14
     */
    void updateResource(RoleResourceDTO dto);

}
