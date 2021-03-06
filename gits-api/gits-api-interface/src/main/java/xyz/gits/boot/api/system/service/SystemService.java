package xyz.gits.boot.api.system.service;

import com.alicp.jetcache.anno.Cached;
import xyz.gits.boot.api.system.dto.UserAddDTO;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.constants.CacheConstants;
import xyz.gits.boot.common.core.response.RestResponse;

/**
 * 系统接口
 *
 * @author songyinyin
 * @date 2020/4/4 下午 09:24
 */
public interface SystemService {

    /**
     * 获取用户相关信息
     *
     * @param userName 用户名
     * @return 当没有该用户时，返回 null 或者空对象
     */
    @Cached(name = CacheConstants.LOGIN_USER, key = "#userName", postCondition = "#result.data != null")
    RestResponse<LoginUser<UserDetailsVO>> loadUserByUsername(String userName);

    /**
     * 自定义查询用户信息
     *
     * @param fieldName 字段名
     * @param value     属性值
     * @return 当没有该用户时，返回 null 或者空对象
     */
    RestResponse<LoginUser<UserDetailsVO>> loadUserByBiz(String fieldName, String value);

    /**
     * 注册用户
     * @param user
     * @return
     */
    RestResponse<LoginUser<UserDetailsVO>> registerUser(UserAddDTO user);

}
