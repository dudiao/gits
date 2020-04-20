package xyz.gits.boot.system.service.impl;

import xyz.gits.boot.api.system.entity.UserRoleRel;
import xyz.gits.boot.system.mapper.UserRoleRelMapper;
import xyz.gits.boot.system.service.IUserRoleRelService;
import xyz.gits.boot.common.basic.BasicServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户拥有角色关联表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Service
public class UserRoleRelServiceImpl extends BasicServiceImpl<UserRoleRelMapper, UserRoleRel> implements IUserRoleRelService {

}
