package xyz.gits.boot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import xyz.gits.boot.api.system.dto.RoleResourceDTO;
import xyz.gits.boot.api.system.entity.RoleResourceRel;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
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
    public void updateResource(RoleResourceDTO dto) {
        this.remove(Wrappers.<RoleResourceRel>query().lambda()
            .eq(RoleResourceRel::getRoleId, dto.getRoleId()));

        if (StrUtil.isBlank(dto.getMenuIds())) {
            return;
        }

        List<RoleResourceRel> list = Arrays
            .stream(dto.getMenuIds().split(","))
            .map(resourceId -> {
                RoleResourceRel roleResourceRel = new RoleResourceRel();
                roleResourceRel.setRoleId(dto.getRoleId());
                roleResourceRel.setResourceId(resourceId);
                return roleResourceRel;
            }).collect(Collectors.toList());

        this.saveBatch(list);
        // TODO 清空用户信息缓存
    }
}
