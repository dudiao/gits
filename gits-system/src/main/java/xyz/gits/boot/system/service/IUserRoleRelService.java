package xyz.gits.boot.system.service;

import xyz.gits.boot.common.core.basic.BasicService;
import xyz.gits.boot.system.dto.user.UserRoleDTO;
import xyz.gits.boot.system.entity.UserRoleRel;

import java.util.List;

/**
 * <p>
 * 系统用户拥有角色关联表 服务类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface IUserRoleRelService extends BasicService<UserRoleRel> {

    /**
     * 授权
     *
     * @param dto 用户角色添加DTO
     * @author null
     * @Date 2020/5/18 16:58
     */
    void authorize(UserRoleDTO dto);

}
