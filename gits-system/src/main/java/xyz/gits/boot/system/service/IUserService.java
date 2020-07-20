package xyz.gits.boot.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import xyz.gits.boot.api.system.dto.UserAddDTO;
import xyz.gits.boot.system.dto.user.SysUserQueryDTO;
import xyz.gits.boot.system.dto.user.UserUpdateDTO;
import xyz.gits.boot.system.entity.User;
import xyz.gits.boot.api.system.vo.LoginUser;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.common.core.basic.BasicService;

import java.util.List;

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
     * 分页查询
     *
     * @return 用户详情 {@link IPage< User>}
     * @author null
     * @date 2020/5/26 17:38
     */
    IPage<User> getPage(SysUserQueryDTO sysUserQueryDTO);

    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名（登录名）
     * @return 前端展示用户详情{@link User}
     * @author null
     * @date 2020/5/26 17:38
     */
    User getByUsername(String userName);

    /**
     * 新增用户
     *
     * @param userAddDTO 用户DTO
     * @return 前端用户展示信息 1、若干密码为空就从配置中心读取默认密码{@link UserDetailsVO}
     * @author null
     * @date 2020/5/26 17:39
     */
    LoginUser<UserDetailsVO> saveUser(UserAddDTO userAddDTO);

    /**
     * 更新用户和其角色
     *
     * @param userUpdateDTO 用户信息传输DTO
     * @author null
     * @date 2020/5/26 17:39
     */
    void updateUser(UserUpdateDTO userUpdateDTO);

    /**
     * 修改用户
     *
     * @param userUpdateDTO 用户信息传输DTO
     * @author null
     * @date 2020/5/26 17:39
     */
    void updateUserInfo(UserUpdateDTO userUpdateDTO);

    /**
     * 删除用户
     *
     * @param userIds 用户ID集合
     * @author null
     * @date 2020/5/26 17:39
     */
    void delete(List<String> userIds);

    /**
     * 修改启用停用标志
     *
     * @param userId   用户ID
     * @param stopFlag 启用停用标志[0:启用;1:停用]
     * @author null
     * @date 2020/5/26 17:39
     */
    void updateUserStatus(String userId, String stopFlag);

    /**
     * 修改密码锁定标志
     *
     * @param userId      用户ID
     * @param pwdLockFlag 密码锁定标志[0-未锁定，1-密码错误锁定]
     * @author null
     * @date 2020/5/26 17:39
     */
    void updateUserLock(String userId, String pwdLockFlag);

    /**
     * 重置密码
     *
     * @param user 用户信息
     * @author null
     * @date 2020/5/26 17:39
     */
    void userPasswordReset(User user);

    /**
     * 获取该机构下用户
     *
     * @param orgId 机构id
     * @return 用户集合 {@link List< User>}
     * @author null
     * @date 2020/5/29 14:44
     */
    List<User> findUserByOrgId(String orgId);

    /**
     * 根据用户id查询用户信息 缓存中有先冲缓存中获取否则从库走获取再缓存信息
     *
     * @param userId 用户id
     * @return 用户信息 {@link User}
     * @author null
     * @date 2020/5/29 17:48
     */
    User getUserById(String userId);
}
