package xyz.gits.boot.api.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.common.core.validate.CreateGroup;
import xyz.gits.boot.common.core.validate.UpdateGroup;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author songyinyin
 * @date 2020/4/9 下午 11:28
 */
@Data
@ApiModel(value = "系统用户传输对象")
public class UserDTO {

    @ApiModelProperty(value = "用户id")
    @NotBlank(groups = UpdateGroup.class)
    private String userId;

    @ApiModelProperty(value = "用户名，登录名")
    @NotBlank(groups = {CreateGroup.class, UpdateGroup.class})
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "所属机构代码")
    private String orgId;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "微信号")
    private String wechat;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "停用标志（0:停用，1:启用）")
    private String stopFlag;

    @ApiModelProperty(value = "停用原因")
    private String stopReason;

    @ApiModelProperty(value = "密码锁定标志（0-未锁定，1-密码错误锁定）")
    private String pwdLockFlag;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色id集合")
    private List<String> role;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
