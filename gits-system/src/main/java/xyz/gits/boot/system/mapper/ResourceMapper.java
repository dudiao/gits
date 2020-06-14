package xyz.gits.boot.system.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.gits.boot.api.system.entity.Resource;
import xyz.gits.boot.common.core.basic.BasicMapper;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface ResourceMapper extends BasicMapper<Resource> {

    /**
     * 通过角色id查询资源
     *
     * @param roleId 角色id
     * @return
     */
    List<Resource> findResourceByRoleId(@Param("roleId") String roleId);

}
