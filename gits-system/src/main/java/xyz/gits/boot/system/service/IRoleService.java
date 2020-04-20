package xyz.gits.boot.system.service;

import xyz.gits.boot.api.system.entity.Role;
import xyz.gits.boot.common.basic.BasicService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface IRoleService extends BasicService<Role> {

    List<Role> getRolesByUserId(String userId);

}
