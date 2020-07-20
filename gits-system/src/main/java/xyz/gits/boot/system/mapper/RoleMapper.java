package xyz.gits.boot.system.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.gits.boot.api.system.enums.Status;
import xyz.gits.boot.system.entity.Role;
import xyz.gits.boot.common.core.basic.BasicMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface RoleMapper extends BasicMapper<Role> {

    /**
     * 根据用户ID和状态查找角色
     *
     * @param userId 用户ID
     * @param status        有效状态[0:有效;1:无效]
     * @return 角色信息 {@link List< Role>}
     * @author null
     * @date 2020/5/27 16:45
     */
    List<Role> getRolesByUserId(@Param("userId") String userId
        , @Param("status") Status status);

}
