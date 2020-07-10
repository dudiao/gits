package xyz.gits.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import xyz.gits.boot.api.system.dto.ResourceDTO;
import xyz.gits.boot.api.system.entity.Resource;
import xyz.gits.boot.api.system.entity.RoleResourceRel;
import xyz.gits.boot.api.system.enums.ResourceType;
import xyz.gits.boot.api.system.enums.VisibleType;
import xyz.gits.boot.api.system.utils.UserUtil;
import xyz.gits.boot.api.system.vo.ResourceTree;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.constants.CacheConstants;
import xyz.gits.boot.common.core.constants.SystemConstants;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.common.core.utils.TreeUtil;
import xyz.gits.boot.system.mapper.ResourceMapper;
import xyz.gits.boot.system.mapper.RoleResourceRelMapper;
import xyz.gits.boot.system.service.IResourceService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Slf4j
public class ResourceServiceImpl extends BasicServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceRelMapper roleResourceRelMapper;

    /**
     * 获取角色关联的资源列表
     *
     * @param roleId 角色id
     * @return
     */
    @Override
    @CachePenetrationProtect
    @Cached(name = CacheConstants.ROLE_RESOURCE, key = "#roleId")
    public List<Resource> findResourceByRoleId(String roleId) {
        return resourceMapper.findResourceByRoleId(roleId);
    }

    /**
     * 删除资源
     *
     * @param resourceId 资源id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(name = CacheConstants.LOGIN_USER)
    @CacheInvalidate(name = CacheConstants.ROLE_RESOURCE)
    public void deleteResource(String resourceId) {
        List<Resource> resourceList = resourceMapper.selectList(Wrappers.<Resource>lambdaQuery()
            .eq(Resource::getParentResourceId, resourceId));
        if (CollUtil.isNotEmpty(resourceList)) {
            log.warn("[资源管理 - 删除资源信息] - 资源[resourceId={}]下有子资源，不能删除", resourceId);
            throw new SystemNoLogException(ResponseCode.RESOURCE_HAVE_SUB);
        }

        // 删除角色资源关系
        roleResourceRelMapper.delete(Wrappers.<RoleResourceRel>query()
            .lambda().eq(RoleResourceRel::getResourceId, resourceId));
        this.removeById(resourceId);
    }

    @Override
    public void saveResource(ResourceDTO dto) {
        Resource resource = getResource(dto);
        resource.setCreateUserId(UserUtil.getUserId());
        resource.setCreateTime(LocalDateTime.now());
        this.save(resource);
    }

    @Override
    public void updateResource(ResourceDTO dto) {
        Resource resource = getResource(dto);
        resource.setUpdateUserId(UserUtil.getUserId());
        resource.setUpdateTime(LocalDateTime.now());
        this.updateById(resource);
    }

    @Override
    public List<ResourceTree> buildTree(Collection<Resource> list) {
        List<ResourceTree> resourceTreeList = list.stream().map(resource -> {
            ResourceTree tree = new ResourceTree();
            BeanUtils.copyPropertiesIgnoreNull(resource, tree);
            tree.setId(resource.getResourceId());
            tree.setParentId(resource.getParentResourceId());
            return tree;
        }).sorted(Comparator.comparingInt(ResourceTree::getOrderNum)).collect(Collectors.toList());
        return TreeUtil.bulid(resourceTreeList, SystemConstants.RESOURCE_ROOT_ID);
    }

    /**
     * 获取实体类，同时校验 parentResourceId 是否存在
     *
     * @param dto
     * @return
     */
    private Resource getResource(ResourceDTO dto) {
        Resource resource = new Resource();
        // 如果是PID为 0000 则为系统资源
        if (StrUtil.equals(dto.getParentResourceId(), SystemConstants.RESOURCE_ROOT_ID)) {
            resource.setResourceType(ResourceType.A);
        } else {
            Resource parentResource = resourceMapper.selectById(resource.getParentResourceId());
            if (null == parentResource) {
                log.warn("[资源管理 - 新增/修改 资源信息] - 资源[resourceId={}, resourceName={}]的父级资源[resourceId={}]不存在",
                    dto.getResourceId(), dto.getResourceName(), dto.getParentResourceId());
                throw new SystemNoLogException(ResponseCode.RESOURCE_PARENT_INVALID);
            }
            resource.setResourceType(ResourceType.fromString(dto.getResourceTypeCode()));
        }

        BeanUtils.copyPropertiesIgnoreNull(dto, resource);
        resource.setVisible(VisibleType.fromString(dto.getVisibleCode()));
        return resource;
    }
}
