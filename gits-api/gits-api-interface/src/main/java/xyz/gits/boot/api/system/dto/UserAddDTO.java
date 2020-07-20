package xyz.gits.boot.api.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class UserAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id", required = true)
    @NotBlank(message = "用户id不能为空")
    @Size(max = 31, message = "用户id不能超过31")
    private String userId;

    @ApiModelProperty(value = "用户名，登录名【不能为中文】", required = true)
    @NotBlank(message = "用户名不能为空")
    @Size(max = 31, message = "用户名，登录名不能超过31")
    private String userName;

    @ApiModelProperty(value = "昵称、姓名", required = true)
    @NotBlank(message = "昵称、姓名不能为空")
    @Size(max = 31, message = "昵称、姓名不能超过31")
    private String nickName;

    @ApiModelProperty(value = "性别[0:女;1:男]")
    @Size(max = 1, message = "性别不能超过1")
    private String sexCode;

    @ApiModelProperty(value = "角色id集合")
    private List<String> role;

    @ApiModelProperty(value = "头像")
    @Size(max = 255, message = "头像不能超过255")
    private String avatar;

    @ApiModelProperty(value = "所属机构代码", required = true)
    @NotBlank(message = "所属机构代码不能为空")
    @Size(max = 31, message = "所属机构代码不能超过255")
    private String orgId;

    @ApiModelProperty(value = "邮箱")
    @Size(max = 127, message = "邮箱不能超过127")
    private String email;

    @ApiModelProperty(value = "手机号码")
    @Size(max = 11, message = "邮箱不能超过11")
    private String mobile;

    @ApiModelProperty(value = "微信号")
    @Size(max = 127, message = "微信号不能超过127")
    private String wechat;

    @ApiModelProperty(value = "备注")
    @Size(max = 511, message = "备注不能超过511")
    private String remark;


    @ApiModelProperty(value = "启停状态[0:启用;1:停用]", required = true)
    @Size(max = 1, message = "启停状态不能超过511")
    @NotBlank(message = "启停状态不能为空")
    private String stopStatusCode;

    @ApiModelProperty(value = "停用原因")
    @Size(max = 255, message = "停用原因不能超过255")
    private String stopReason;

    @ApiModelProperty(value = "giteeId")
    @Size(max = 63, message = "giteeId")
    private String giteeId;
}
