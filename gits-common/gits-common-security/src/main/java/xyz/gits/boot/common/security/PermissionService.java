package xyz.gits.boot.common.security;

import lombok.extern.slf4j.Slf4j;
import xyz.gits.boot.api.system.utils.UserUtil;

import java.util.Set;

/**
 * 自定义权限实现
 *
 * @author songyinyin
 * @date 2020/3/6 上午 10:58
 */
@Slf4j
public class PermissionService {

    public boolean permission(String permission) {
        Set<String> userPermissions = UserUtil.getUserPermissions();
        for (String userPermission : userPermissions) {
            if (permission.matches(userPermission)) {
                return true;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("用户userId={}, userName={} 权限不足以访问[{}], 用户具有权限：{}, 访问", UserUtil.getUserId(),
                UserUtil.getUserName(), permission, UserUtil.getUserPermissions());
        } else {
            log.info("用户userId={}, userName={} 权限不足以访问[{}]", UserUtil.getUserId(), UserUtil.getUserName(), permission);
        }
        return false;
    }
}
