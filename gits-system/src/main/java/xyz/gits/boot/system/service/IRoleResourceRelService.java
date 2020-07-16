package xyz.gits.boot.system.service;

import xyz.gits.boot.api.system.dto.RoleResourceDTO;
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
     * 更新角色对应的资源
     * @param dto
     */
    void updateResource(RoleResourceDTO dto);

}
