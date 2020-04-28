package xyz.gits.boot.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import xyz.gits.boot.api.system.entity.Resource;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.system.mapper.ResourceMapper;
import xyz.gits.boot.system.service.IResourceService;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public class ResourceServiceImpl extends BasicServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> selectListByUser(String userId) {
        return resourceMapper.selectListByUser(userId, null, null);
    }
}
