package xyz.gits.boot.system.mapper;

import org.apache.ibatis.annotations.Param;
import xyz.gits.boot.api.system.entity.Role;
import xyz.gits.boot.common.basic.BasicMapper;

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

    List<Role> getRolesByUserId(@Param("userId") String userId);

}
