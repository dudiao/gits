package xyz.gits.boot.system.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.gits.boot.api.system.enums.VisibleStatus;
import xyz.gits.boot.common.core.basic.BasicMapper;
import xyz.gits.boot.system.entity.Resource;

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
     * @param roleId        角色id
     * @param visibleStatus 显示状态
     * @return {@link List<Resource>}
     * @author null
     * @date 2020/7/17 14:46
     */
    List<Resource> findResourceByRoleId(@Param("roleId") String roleId, @Param("visible") VisibleStatus visibleStatus);


    /**
     * 根据用户ID查询权限
     *
     * @param userId 角色ID
     * @return 去重的权限串集合 {@link List< String>}
     * @author null
     * @date 2020/5/27 16:47
     */
    List<String> selectResourcePermsByUserId(String userId);

}
