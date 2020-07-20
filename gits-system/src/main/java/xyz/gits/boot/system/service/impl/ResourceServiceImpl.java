package xyz.gits.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import xyz.gits.boot.api.system.enums.ResourceTarget;
import xyz.gits.boot.api.system.enums.ResourceType;
import xyz.gits.boot.api.system.enums.VisibleStatus;
import xyz.gits.boot.api.system.utils.AuthUtils;
import xyz.gits.boot.system.vo.resource.ResourceTree;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.constants.CacheConstants;
import xyz.gits.boot.common.core.constants.SystemConstants;
import xyz.gits.boot.common.core.exception.SystemException;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.common.core.utils.TreeUtil;
import xyz.gits.boot.system.dto.resource.ResourceDTO;
import xyz.gits.boot.system.entity.Resource;
import xyz.gits.boot.system.entity.RoleResourceRel;
import xyz.gits.boot.system.mapper.ResourceMapper;
import xyz.gits.boot.system.mapper.RoleResourceRelMapper;
import xyz.gits.boot.system.service.IResourceService;

import java.time.LocalDateTime;
import java.util.*;
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
     * 获取角色关联的资源列表(状态为显示)
     *
     * @param roleId 角色id
     * @return {@link List<Resource>}
     * @author null
     * @date 2020/7/20 10:25
     */
    @Override
    @CachePenetrationProtect
    @Cached(name = CacheConstants.ROLE_RESOURCE, key = "#roleId")
    public List<Resource> findResourceByRoleId(String roleId) {
        return resourceMapper.findResourceByRoleId(roleId, VisibleStatus.SHOW);
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
        resource.setVisible(VisibleStatus.fromString(dto.getVisibleCode()));
        return resource;
    }

    /**
     * 修改资源信息
     *
     * @param resourceDTO 修改资源信息DTO
     * @author null
     * @date 2020/5/25 17:31
     */
    @Override
    public void updateResource(ResourceDTO resourceDTO) {

        // 根据ID判断资源是否存在
        Resource resource = resourceMapper.selectById(resourceDTO.getResourceId());
        if (BeanUtil.isEmpty(resource)) {
            log.warn("[资源管理-修改资源信息]-资源不存在, 资源id:resourceId={}, 资源名称:resourceName={}", resourceDTO.getResourceId(), resourceDTO.getResourceName());
            throw new SystemNoLogException(ResponseCode.RESOURCE_NOT_FIND);
        }

        // 根据父ID查找资源
        Resource parentResource = resourceMapper.selectById(resource.getParentResourceId());
        if (!StrUtil.equals(resource.getParentResourceId(), SystemConstants.TOP_RESOURCE) && parentResource == null) {
            log.warn("[资源管理-修改资源信息]-此资源的父资源不存在, 资源id:resourceId={}, 资源名称:resourceName={},父资源id:ParentResourceId={}",
                resourceDTO.getResourceId(), resourceDTO.getResourceName(), resourceDTO.getParentResourceId());
            throw new SystemNoLogException(ResponseCode.RESOURCE_PARENT_NOT_FIND);
        }

        BeanUtils.copyPropertiesIgnoreNull(resourceDTO, resource);
        resource.setResourceType(ResourceType.fromString(resourceDTO.getResourceTypeCode()));
        resource.setVisible(VisibleStatus.fromString(resourceDTO.getVisibleCode()));
        resource.setTarget(ResourceTarget.fromString(resourceDTO.getTargetCode()));
        resource.setUpdateUserId(AuthUtils.getUserId());
        resource.setUpdateTime(LocalDateTime.now());
        resourceMapper.updateById(resource);
    }


    /**
     * 新增资源信息
     *
     * @param resourceDTO 新增资源传输信息
     * @author null
     * @date 2020/5/25 17:32
     */
    @Override
    public void saveResource(ResourceDTO resourceDTO) {
        Resource resource = new Resource();
        BeanUtils.copyPropertiesIgnoreNull(resourceDTO, resource);
        // 根据当前资源ID查询资源, 判断是否重复
        Resource resourceNew = resourceMapper.selectById(resourceDTO.getResourceId());
        if (!BeanUtil.isEmpty(resourceNew)) {
            log.warn("[资源管理-新增资源信息]-资源已存在,资源id:resourceId={}, 资源名称:resourceName={}", resourceDTO.getResourceId(), resourceDTO.getResourceName());
            throw new SystemException(ResponseCode.RESOURCE_IS_EXIST);
        }
        // 如果是PID为0则为顶级资源
        if (StrUtil.equals(resource.getParentResourceId(), SystemConstants.TOP_RESOURCE)) {
            resource.setResourceType(ResourceType.A);
        } else {
            // 否则根据父ID查找资源
            Resource parentResource = resourceMapper.selectById(resource.getParentResourceId());
            if (BeanUtil.isEmpty(parentResource)) {
                log.warn("[资源管理-修改资源信息]-此资源的父资源不存在, 资源id:resourceId={}, 资源名称:resourceName={},父资源id:ParentResourceId={}",
                    resourceDTO.getResourceId(), resourceDTO.getResourceName(), resourceDTO.getParentResourceId());
                throw new SystemNoLogException(ResponseCode.RESOURCE_PARENT_NOT_FIND);
            }
        }

        resource.setResourceType(ResourceType.fromString(resourceDTO.getResourceTypeCode()));
        resource.setVisible(VisibleStatus.fromString(resourceDTO.getVisibleCode()));
        resource.setTarget(ResourceTarget.fromString(resourceDTO.getTargetCode()));
        resource.setCreateUserId(AuthUtils.getUserId());
        resource.setCreateTime(LocalDateTime.now());
        this.save(resource);
    }

    @Override
    public Set<String> selectResourcePermsByUserId(String userId) {
        // 根据用户id查询相关权限集合
        List<String> permissions = resourceMapper.selectResourcePermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : permissions) {
            if (StrUtil.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }
}
