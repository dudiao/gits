package xyz.gits.boot.api.system.service;

import xyz.gits.boot.api.system.vo.UserVO;

import java.util.Set;

/**
 * @author songyinyin
 * @date 2020/4/4 下午 09:24
 */
public interface SystemService {

    /**
     * 获取用户相关信息
     * @param userName 用户名
     * @return 当没有该用户时，返回 null
     */
    UserVO loadUserByUsername(String userName);

}
