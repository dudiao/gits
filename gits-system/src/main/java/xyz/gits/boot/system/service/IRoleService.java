package xyz.gits.boot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.gits.boot.api.system.enums.Status;
import xyz.gits.boot.system.dto.role.RoleQueryDTO;
import xyz.gits.boot.system.dto.role.RoleSaveDTO;
import xyz.gits.boot.system.dto.role.RoleUpdateDTO;
import xyz.gits.boot.system.entity.Role;
import xyz.gits.boot.common.core.basic.BasicService;
import xyz.gits.boot.system.vo.role.RoleDetailVO;

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

    /**
     * 根据用户ID查找角色
     *
     * @param userId
     * @return 角色对象 {@link List<Role>}
     * @author null
     * @date 2020/5/26 17:22
     */
    List<Role> getRolesByUserId(String userId,Status status);

    /**
     * 新增角色
     *
     * @param roleDTO 角色DTO传输对象
     * @author null
     * @date 2020/5/26 17:26
     */
    void saveRole(RoleSaveDTO roleDTO);

    /**
     * 修改角色
     *
     * @param roleDTO 角色DTO传输对象
     * @author null
     * @date 2020/5/26 17:27
     */
    void updateRole(RoleUpdateDTO roleDTO);

    /**
     * 根据角色id查找角色
     *
     * @param roleId 角色ID
     * @return 前端角色展示{@link RoleVO}
     * @author null
     * @date 2020/5/26 17:27
     */
    RoleDetailVO getRoleById(String roleId);

    /**
     * 根据角色id删除角色
     *
     * @param roleId 角色ID
     * @author null
     * @date 2020/5/26 17:28
     */
    void deleteRole(String roleId);

    /**
     * 角色分页查询
     *
     * @return 角色信息 {@link IPage< Role>}
     * @author null
     * @date 2020/5/26 17:28
     */
    IPage<Role> getPage(RoleQueryDTO roleQueryDTO);

    /**
     * 获取所有角色
     *
     * @return {@link List<Role>}
     * @author null
     * @date 2020/5/26 17:28
     */
    List<Role> getAllRoleByStatus(String status);

    /**
     * 修改角色启停标志（0：启用 1：停用）
     *
     * @param roleId 角色ID
     * @param status 启用停用标志[0:启用;1:停用]
     * @author null
     * @date 2020/6/11 14:57
     */
    void updateRoleStatus(String roleId, Status status);

}
