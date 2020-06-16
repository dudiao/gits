package xyz.gits.boot.api.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 新增用户
 *
 * @author songyinyin
 * @date 2020/4/9 下午 11:28
 */
@Data
@ApiModel(value = "新增系统用户传输对象")
public class UserSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名，登录名")
    @NotBlank(message = "用户名不能为空")
    @Size(max = 64, message = "用户名长度不能超过64位")
    private String userName;

    @ApiModelProperty(value = "昵称")
    @Size(max = 64, message = "昵称长度不能超过64位")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "所属机构代码")
    @Size(max = 32, message = "机构代码不能超过32位")
    private String orgId;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "微信号")
    private String wechat;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "gitee用户id")
    private String giteeId;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色id集合")
    private List<String> role;
}
