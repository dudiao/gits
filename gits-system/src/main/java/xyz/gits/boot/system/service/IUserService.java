package xyz.gits.boot.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.gits.boot.api.system.dto.UserUpdateDTO;
import xyz.gits.boot.api.system.dto.UserSaveDTO;
import xyz.gits.boot.api.system.entity.User;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserVO;
import xyz.gits.boot.common.core.basic.BasicService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
public interface IUserService extends BasicService<User> {


    /**
     * 分页获取用户列表
     *
     * @param queryWrapper
     * @return
     */
    IPage<User> getPage(Wrapper<User> queryWrapper);

    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名（登录名）
     */
    User getByUsername(String userName);

    LoginUser saveUser(UserSaveDTO userDTO);

    /**
     * 更新用户和其角色
     *
     * @param userDTO
     */
    void updateUser(UserUpdateDTO userDTO);

    /**
     * 修改本人信息（密码等）
     *
     * @param userDTO
     */
    void updateUserInfo(UserUpdateDTO userDTO);
}
