package xyz.gits.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import xyz.gits.boot.api.system.enums.Status;
import xyz.gits.boot.api.system.utils.AuthUtils;
import xyz.gits.boot.api.system.utils.ConvertUtils;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.dto.role.RoleQueryDTO;
import xyz.gits.boot.system.dto.role.RoleSaveDTO;
import xyz.gits.boot.system.dto.role.RoleUpdateDTO;
import xyz.gits.boot.system.entity.Org;
import xyz.gits.boot.system.entity.Role;
import xyz.gits.boot.system.entity.RoleResourceRel;
import xyz.gits.boot.system.entity.UserRoleRel;
import xyz.gits.boot.system.mapper.RoleMapper;
import xyz.gits.boot.system.mapper.RoleResourceRelMapper;
import xyz.gits.boot.system.mapper.UserRoleRelMapper;
import xyz.gits.boot.system.service.IOrgService;
import xyz.gits.boot.system.service.IRoleService;
import xyz.gits.boot.system.vo.role.RoleDetailVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Slf4j
public class RoleServiceImpl extends BasicServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IOrgService orgService;

    @Autowired
    private UserRoleRelMapper userRoleRelMapper;

    @Autowired
    private RoleResourceRelMapper roleResourceRelMapper;

    /**
     * 根据用户ID和状态查找角色
     *
     * @param userId
     * @return 角色对象 {@link List< Role>}
     * @author null
     * @date 2020/5/26 17:22
     */
    @Override
    public List<Role> getRolesByUserId(String userId, Status status) {
        return roleMapper.getRolesByUserId(userId, status);
    }

    /**
     * 新增角色
     *
     * @param roleDTO 角色DTO传输对象
     * @author null
     * @date 2020/5/26 17:26
     */
    @Override
    public void saveRole(RoleSaveDTO roleDTO) {
        List<Role> roleList = roleMapper.selectList(new QueryWrapper<Role>().lambda().eq(Role::getRoleCode, roleDTO.getRoleCode()));
        if (CollectionUtil.isNotEmpty(roleList)) {
            log.warn("[角色管理-新增角色信息]-角色代码已存在, 角色代码:roleCode={}", roleDTO.getRoleCode());
            throw new SystemNoLogException(ResponseCode.ROLE_IS_EXIST);
        }
        //将填写的信息对拷进role
        Role role = new Role();
        BeanUtils.copyPropertiesIgnoreNull(roleDTO, role);
        role.setCreateUserId(AuthUtils.getUserId());
        role.setCreateTime(LocalDateTime.now());
        role.setCreateOrgId(AuthUtils.getUserOrgId());
        role.setStatus(Status.VALID);
        roleMapper.insert(role);
    }

    /**
     * 修改角色
     *
     * @param roleDTO 角色DTO传输对象
     * @author null
     * @date 2020/5/26 17:27
     */
    @Override
    public void updateRole(RoleUpdateDTO roleDTO) {
        //根据填写的roleId去数据库查找是否存在
        Role role = roleMapper.selectById(roleDTO.getRoleId());
        if (BeanUtil.isEmpty(role)) {
            log.warn("[角色管理-修改角色信息]-角色不存在,角色id:resourceId={}", roleDTO.getRoleId());
            throw new SystemNoLogException(ResponseCode.ROLE_IS_NOT_EXIST);
        }

        if (!roleDTO.getRoleCode().equals(role.getRoleCode())) {
            List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().lambda().eq(Role::getRoleCode, roleDTO.getRoleCode()));
            if (roles.size() > 2) {
                log.warn("[角色管理-新增角色信息]-角色代码已存在, 角色代码:roleKey={}", roleDTO.getRoleCode());
                throw new SystemNoLogException(ResponseCode.ROLE_IS_EXIST);
            }
        }
        BeanUtils.copyPropertiesIgnoreNull(roleDTO, role);
        role.setUpdateUserId(AuthUtils.getUserId());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.updateById(role);
    }

    /**
     * 根据角色id查找角色
     *
     * @param roleId 角色ID
     * @return 前端角色展示{@link RoleVO}
     * @author null
     * @date 2020/5/26 17:27
     */
    @Override
    public RoleDetailVO getRoleById(String roleId) {
        Role role = roleMapper.selectById(roleId);
        if (BeanUtil.isEmpty(role)) {
            log.warn("[角色管理-根据角色id查找角色]-当前角色不存在,角色id:RoleId={}", roleId);
            throw new SystemNoLogException(ResponseCode.ROLE_IS_NOT_EXIST);
        }
        RoleDetailVO roleDetailVO = new RoleDetailVO();
        BeanUtils.copyPropertiesIgnoreNull(role, roleDetailVO);
        ConvertUtils.convertUser(roleDetailVO);
        Map<String, Org> orgMap = orgService.getOrgMap(null);
        Org org = orgMap.get(role.getCreateOrgId());
        if (BeanUtil.isNotEmpty(org)) {
            roleDetailVO.setCreateOrgName(org.getOrgName());
        }
        return roleDetailVO;
    }

    /**
     * 根据角色id删除角色
     *
     * @param roleId 角色ID
     * @author null
     * @date 2020/5/26 17:28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(String roleId) {
        Role role = roleMapper.selectById(roleId);
        if (BeanUtil.isEmpty(role)) {
            log.warn("[角色管理-删除角色]-当前角色不存在,角色id:RoleId={}", roleId);
            throw new SystemNoLogException(ResponseCode.ROLE_IS_NOT_EXIST);
        }
        userRoleRelMapper.delete(new QueryWrapper<UserRoleRel>().lambda().eq(UserRoleRel::getRoleId, roleId));
        roleResourceRelMapper.delete(new QueryWrapper<RoleResourceRel>().lambda().eq(RoleResourceRel::getRoleId, roleId));
        roleMapper.deleteById(roleId);

    }

    /**
     * 角色分页查询
     *
     * @return 角色信息 {@link IPage< Role>}
     * @author null
     * @date 2020/5/26 17:28
     */
    @Override
    public IPage<Role> getPage(RoleQueryDTO roleQueryDTO) {
        QueryWrapper<Role> queryWrapper = parseParameter();
        queryWrapper.lambda()
            .like(StrUtil.isNotEmpty(roleQueryDTO.getRoleCode()), Role::getRoleCode, roleQueryDTO.getRoleCode())
            .like(StrUtil.isNotEmpty(roleQueryDTO.getRoleName()), Role::getRoleName, roleQueryDTO.getRoleName());
        return page(queryWrapper).setCurrent(roleQueryDTO.getCurrentPage()).setSize(roleQueryDTO.getPageSize());
    }

    /**
     * 获取所有角色
     *
     * @return {@link List< Role>}
     * @author null
     * @date 2020/5/26 17:28
     */
    @Override
    public List<Role> getAllRoleByStatus(String status) {
        return roleMapper.selectList(new QueryWrapper<Role>().lambda().eq(Role::getStatus, status));
    }

    /**
     * 修改角色启停标志（0:启用 1:停用）
     *
     * @param roleId 角色ID
     * @param status 启用停用标志[0:启用;1:停用]
     * @author null
     * @date 2020/6/11 14:58
     */
    @Override
    public void updateRoleStatus(String roleId, Status status) {
        Role role = roleMapper.selectById(roleId);
        if (BeanUtil.isEmpty(role)) {
            log.warn("[角色管理-修改角色启停标志]-当前角色不存在无法修改,角色id:RoleId={}", roleId);
            throw new SystemNoLogException(ResponseCode.ROLE_IS_NOT_EXIST);
        }
        role.setStatus(status);
        roleMapper.updateById(role);
    }
}
