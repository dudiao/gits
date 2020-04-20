package xyz.gits.boot.system.service.impl;

import xyz.gits.boot.api.system.entity.RoleResourceRel;
import xyz.gits.boot.system.mapper.RoleResourceRelMapper;
import xyz.gits.boot.system.service.IRoleResourceRelService;
import xyz.gits.boot.common.basic.BasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色资源关联表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Service
public class RoleResourceRelServiceImpl extends BasicServiceImpl<RoleResourceRelMapper, RoleResourceRel> implements IRoleResourceRelService {

}
