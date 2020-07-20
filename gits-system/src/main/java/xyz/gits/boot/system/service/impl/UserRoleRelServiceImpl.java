package xyz.gits.boot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.system.dto.user.UserRoleDTO;
import xyz.gits.boot.system.entity.User;
import xyz.gits.boot.system.entity.UserRoleRel;
import xyz.gits.boot.system.mapper.UserMapper;
import xyz.gits.boot.system.mapper.UserRoleRelMapper;
import xyz.gits.boot.system.service.IUserRoleRelService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户拥有角色关联表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Slf4j
public class UserRoleRelServiceImpl extends BasicServiceImpl<UserRoleRelMapper, UserRoleRel> implements IUserRoleRelService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleRelMapper userRoleRelMapper;

    /**
     * 授权
     *
     * @param dto 用户角色添加DTO
     * @author null
     * @Date 2020/5/18 16:58
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authorize(UserRoleDTO dto) {
        User user = userMapper.selectById(dto.getUserId());
        if (null == user) {
            log.warn("[用户管理-添加用户角色信息]-用户id不存在，用户id:userId={}", dto.getUserId());
            throw new SystemNoLogException(ResponseCode.USER_NOT_EXIST);
        }
        // 构造查询Wrapper
        QueryWrapper<UserRoleRel> qw = new QueryWrapper<>();
        qw.lambda().eq(UserRoleRel::getUserId, dto.getUserId());
        userRoleRelMapper.delete(qw);
        if (CollectionUtil.isNotEmpty(dto.getRoleIds())) {
            //构建用户角色集合对象
            List<UserRoleRel> collect = dto.getRoleIds().stream().map(e -> {
                UserRoleRel userRole = new UserRoleRel();
                userRole.setRoleId(e);
                userRole.setUserId(dto.getUserId());
                return userRole;
            }).collect(Collectors.toList());
            //批量插入
            saveBatch(collect, collect.size());
        }
    }

}
