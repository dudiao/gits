package xyz.gits.boot.system.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.gits.boot.api.system.entity.Resource;
import xyz.gits.boot.common.basic.BasicMapper;
import xyz.gits.boot.system.enums.ResourceType;

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
     * 查询用户的权限
     *
     * @param userId
     * @param systemId
     * @param resourceTypes
     * @return
     */
    List<Resource> selectListByUser(@Param("userId") String userId,
                                    @Param("systemId") String systemId,
                                    @Param("resourceTypes") ResourceType... resourceTypes);

}
