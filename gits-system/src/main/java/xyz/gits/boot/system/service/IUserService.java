package xyz.gits.boot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.gits.boot.api.system.dto.UserDTO;
import xyz.gits.boot.api.system.entity.User;
import xyz.gits.boot.common.basic.BasicService;
import xyz.gits.boot.common.response.RestResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface IUserService extends BasicService<User> {


    IPage<User> getPage();

    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名（登录名）
     */
    User getByUsername(String userName);

    void saveUser(UserDTO userDTO);

    /**
     * 更新用户和其角色
     *
     * @param userDTO
     */
    void updateUser(UserDTO userDTO);

    RestResponse updateUserInfo(UserDTO userDTO);
}
