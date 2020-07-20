package xyz.gits.boot.system.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.common.core.basic.BaseQueryDTO;

import javax.validation.constraints.Size;

/**
 * 用户查询参数
 *
 * @author null
 * @date 2020/5/18 17:29
 * @since 1.0
 */
@Data
@ApiModel("用户查询参数")
public class UserQueryDTO extends BaseQueryDTO {

    /**
     * 用户ID（工号）
     */
    @ApiModelProperty(value = "用户ID（工号）")
    private String userId;

    /**
     * 机构Id
     */
    @ApiModelProperty(value = "机构Id")
    private String orgId;


    /**
     * 帐号启用状态（1正常 0停用）
     */
    @ApiModelProperty(value = "帐号启用状态（0启用 1停用）")
    @Size(max = 1, message = "启停状态不能超过1")
    private String stopStatusCode;

    /**
     * 账号锁定状态
     */
    @ApiModelProperty(value = "帐号锁定状态（0正常(未锁定) 1停用（已锁定））")
    @Size(max = 1, message = "锁定状态不能超过1")
    private String pwdLockStatusCode;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "昵称、姓名")
    private String nickName;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名，登录名【不能为中文】")
    private String userName;
}
