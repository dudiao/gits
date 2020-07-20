package xyz.gits.boot.api.system.utils;

import cn.hutool.core.bean.BeanUtil;
import xyz.gits.boot.api.system.service.CacheService;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.basic.BasicVO;
import xyz.gits.boot.common.core.utils.SpringContextHolder;

import java.util.List;
import java.util.Map;

/**
 * 补全用户信息工具类
 *
 * @author null
 * @date 2020/07/07/17:54
 */
public class ConvertUtils {

    private static CacheService cacheService = SpringContextHolder.getBean(CacheService.class);


    /**
     * 补全视图对象信息
     *
     * @param vo 视图
     * @return
     * @author null
     * @date 2020/7/8 15:12
     */
    public static <V extends BasicVO> void convertUser(V vo) {
        //获取用户map信息
        Map<String, UserDetailsVO> userMap = cacheService.getUserMap();
        convertUserByUserMap(vo, userMap);
    }

    /**
     * 补全集合用户信息
     *
     * @param list 视图集合
     * @return
     * @author null
     * @date 2020/7/8 15:12
     */
    public static <V extends BasicVO> void convertUserList(List<V> list) {
        // 获取用户map信息
        Map<String, UserDetailsVO> userMap = cacheService.getUserMap();
        for (V v : list) {
            convertUserByUserMap(v, userMap);
        }
    }

    /**
     * 补全视图对象中的用户名称
     *
     * @param vo      视图
     * @param userMap 用户map
     * @return
     * @author null
     * @date 2020/7/8 15:10
     */
    public static <V extends BasicVO> void convertUserByUserMap(V vo, Map<String, UserDetailsVO> userMap) {

        // 补全 创建用户名称
        if (vo.getCreateUserId() != null) {
            UserDetailsVO createUser = userMap.get(vo.getCreateUserId());
            if (BeanUtil.isNotEmpty(createUser)) {
                vo.setCreateUserNickName(createUser.getNickName());
            }
        }
        // 补全 修改用户名称
        if (vo.getUpdateUserId() != null) {
            UserDetailsVO updateUser = userMap.get(vo.getUpdateUserId());
            if (BeanUtil.isNotEmpty(updateUser)) {
                vo.setUpdateUserNickName(updateUser.getNickName());
            }
        }
    }
}