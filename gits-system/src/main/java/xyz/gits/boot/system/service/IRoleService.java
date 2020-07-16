package xyz.gits.boot.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.gits.boot.api.system.dto.RoleSaveDTO;
import xyz.gits.boot.api.system.dto.RoleUpdateDTO;
import xyz.gits.boot.system.entity.Role;
import xyz.gits.boot.common.core.basic.BasicService;

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
     * 保存角色
     * @param dto
     * @return
     */
    void saveRole(RoleSaveDTO dto);

    /**
     * 修改角色
     * @param dto
     * @return
     */
    void updateRole(RoleUpdateDTO dto);

    List<Role> getRolesByUserId(String userId);

    /**
     * 分页获取角色列表
     * @param queryWrapper
     * @return
     */
    IPage<Role> getPage(Wrapper<Role> queryWrapper);

}
