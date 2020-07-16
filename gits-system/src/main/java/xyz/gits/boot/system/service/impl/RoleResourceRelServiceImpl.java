package xyz.gits.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.transaction.annotation.Transactional;
import xyz.gits.boot.api.system.dto.RoleResourceDTO;
import xyz.gits.boot.system.entity.RoleResourceRel;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.constants.CacheConstants;
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
public class RoleResourceRelServiceImpl extends BasicServiceImpl<RoleResourceRelMapper, RoleResourceRel> implements IRoleResourceRelService {

    /**
     * 更新角色对应的资源
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(name= CacheConstants.LOGIN_USER)
    @CacheInvalidate(name= CacheConstants.ROLE_RESOURCE, key="#dto.roleId")
    public void updateResource(RoleResourceDTO dto) {
        this.remove(Wrappers.<RoleResourceRel>query().lambda()
            .eq(RoleResourceRel::getRoleId, dto.getRoleId()));

        if (StrUtil.isBlank(dto.getResourceIds())) {
            return;
        }

        List<RoleResourceRel> list = Arrays
            .stream(dto.getResourceIds().split(","))
            .map(resourceId -> {
                RoleResourceRel roleResourceRel = new RoleResourceRel();
                roleResourceRel.setRoleId(dto.getRoleId());
                roleResourceRel.setResourceId(resourceId);
                return roleResourceRel;
            }).collect(Collectors.toList());

        this.saveBatch(list);
    }
}
