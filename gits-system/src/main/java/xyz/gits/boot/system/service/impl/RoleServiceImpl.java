package xyz.gits.boot.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.gits.boot.api.system.dto.RoleSaveDTO;
import xyz.gits.boot.api.system.dto.RoleUpdateDTO;
import xyz.gits.boot.api.system.entity.Role;
import xyz.gits.boot.api.system.enums.StopFlag;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.common.security.UserUtil;
import xyz.gits.boot.system.mapper.RoleMapper;
import xyz.gits.boot.system.service.IRoleService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public class RoleServiceImpl extends BasicServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 保存角色
     *
     * @param dto
     * @return
     */
    @Override
    public void saveRole(RoleSaveDTO dto) {
        Role role = new Role();
        BeanUtils.copyPropertiesIgnoreNull(dto, role);
        role.setCreateUserId(UserUtil.getUserId());
        role.setCreateTime(LocalDateTime.now());
        role.setCreateOrgId(UserUtil.getUserOrgId());
        role.setStopFlag(StopFlag.ENABLE);
        baseMapper.insert(role);
    }

    /**
     * 修改角色
     *
     * @param dto
     * @return
     */
    @Override
    public void updateRole(RoleUpdateDTO dto) {
        Role role = new Role();
        BeanUtils.copyPropertiesIgnoreNull(dto, role);
        role.setUpdateUserId(UserUtil.getUserId());
        role.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(role);
    }

    @Override
    public List<Role> getRolesByUserId(String userId) {
        return roleMapper.getRolesByUserId(userId);
    }

    @Override
    public IPage<Role> getPage(Wrapper<Role> queryWrapper) {
        if (ObjectUtil.isEmpty(queryWrapper)) {
            return page(parseParameter());
        }
        return page(queryWrapper);
    }

}
