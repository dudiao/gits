package xyz.gits.boot.system.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.gits.boot.api.system.service.CacheService;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.entity.User;
import xyz.gits.boot.system.mapper.UserMapper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author songyinyin
 * @date 2020/7/10 12:03
 */
@Slf4j
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, UserDetailsVO> getUserMap() {
        List<User> users = userMapper.selectList(null);

        return users.stream().map(e -> {
            UserDetailsVO vo = new UserDetailsVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toMap(UserDetailsVO::getUserId, Function.identity()));

    }
}
