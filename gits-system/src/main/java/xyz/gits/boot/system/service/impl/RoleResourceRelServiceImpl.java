package xyz.gits.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.system.dto.role.RoleResourceDTO;
import xyz.gits.boot.system.entity.Role;
import xyz.gits.boot.system.entity.RoleResourceRel;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.constants.CacheConstants;
import xyz.gits.boot.system.mapper.RoleMapper;
import xyz.gits.boot.system.mapper.RoleResourceRelMapper;
import xyz.gits.boot.system.service.IRoleResourceRelService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色资源关联表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Slf4j
public class RoleResourceRelServiceImpl extends BasicServiceImpl<RoleResourceRelMapper, RoleResourceRel> implements IRoleResourceRelService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceRelMapper roleResourceRelMapper;

    /**
     * 给角色授权资源
     *
     * @param dto 角色资源添加DTO{@link RoleResourceDTO}
     * @author null
     * @date 2020/5/26 17:14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(name= CacheConstants.LOGIN_USER)
    @CacheInvalidate(name= CacheConstants.ROLE_RESOURCE, key="#dto.roleId")
    public void updateResource(RoleResourceDTO dto) {
        Role role = roleMapper.selectById(dto.getRoleId());
        if (BeanUtil.isEmpty(role)) {
            log.warn("[角色管理-修改角色信息] - 角色不存在,角色id:role={}", dto.getRoleId());
            throw new SystemNoLogException(ResponseCode.ROLE_IS_NOT_EXIST);
        }

        //构建角色id查询；构建查询queryWrapper
        QueryWrapper<RoleResourceRel> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(RoleResourceRel::getRoleId, dto.getRoleId());
        //删除当前角色资源集合对象
        roleResourceRelMapper.delete(queryWrapper);
        if (CollectionUtil.isNotEmpty(dto.getResourceIds())) {
            //构建新的角色资源集合对象
            List<RoleResourceRel> collect = dto.getResourceIds().stream().map(e -> {
                RoleResourceRel roleResourceRel = new RoleResourceRel();
                roleResourceRel.setResourceId(e);
                roleResourceRel.setRoleId(dto.getRoleId());
                return roleResourceRel;
            }).collect(Collectors.toList());
            //批量插入
            saveBatch(collect, collect.size());
        }
    }
}
