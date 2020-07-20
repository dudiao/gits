package xyz.gits.boot.system.vo.log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.api.system.enums.Login;
import xyz.gits.boot.api.system.enums.LoginType;

import java.time.LocalDate;

/**
 * 登录日志VO
 *
 * @author null
 * @date 2020/07/09/10:23
 */
@Data
@Api("登录日志VO")
public class LoginLogVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("登录IP")
    private String loginIp;

    @ApiModelProperty("登录时间")
    private LocalDate loginTime;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("登录结果")
    private Login successFlag;

    @ApiModelProperty("登录失败原因")
    private String failReason;

    @ApiModelProperty("机构ID")
    private String orgId;

    @ApiModelProperty("机构名称")
    private String orgName;

    @ApiModelProperty("登录方式")
    private LoginType loginType;

    @ApiModelProperty("备注")
    private String remark;
}