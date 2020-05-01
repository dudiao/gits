package xyz.gits.boot.api.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.api.system.entity.User;

import java.util.Set;

/**
 * @author songyinyin
 * @date 2020/3/26 下午 09:18
 */
@Data
@ApiModel(value = "前端用户展示对象")
public class UserVO extends User {

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    /**
     * 权限标识集合
     */
    @ApiModelProperty(value = "权限标识集合")
    private Set<String> permissions;
    /**
     * 角色集合
     */
    @ApiModelProperty(value = "角色标识集合")
    private Set<String> roles;

    // session

    @ApiModelProperty(value = "登录ip")
    private String loginIp;

    @ApiModelProperty(value = "sessionId")
    private String sessionId;
}
