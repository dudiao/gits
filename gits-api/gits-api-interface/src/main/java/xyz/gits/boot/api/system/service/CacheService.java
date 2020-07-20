package xyz.gits.boot.api.system.service;


import xyz.gits.boot.api.system.vo.UserDetailsVO;

import java.util.Map;

/**
 * 缓存 接口
 *
 * @author songyinyin
 * @date 2020/7/9 17:53
 */
public interface CacheService {

    /**
     * 获取所有用户map
     *
     * @return key 为 userId
     * @author songyinyin
     * @date 2020/7/9
     */
    Map<String, UserDetailsVO> getUserMap();
}
