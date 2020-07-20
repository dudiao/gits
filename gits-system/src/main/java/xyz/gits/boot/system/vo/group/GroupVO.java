package xyz.gits.boot.system.vo.group;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.api.system.enums.StopStatus;
import xyz.gits.boot.api.system.enums.Whether;
import xyz.gits.boot.common.core.basic.BasicVO;

/**
 * 数据组vo对象
 *
 * @author null
 * @date 2020/05/29/16:49
 */
@Data
public class GroupVO extends BasicVO {

    @ApiModelProperty("组key")
    private String groupKey;

    @ApiModelProperty("上级组key")
    private String upGroupKey;

    @ApiModelProperty("组名称")
    private String groupName;

    @ApiModelProperty("启停状态[0:启用;1:停用]")
    private StopStatus stopStatus;

    @ApiModelProperty("是否为系统初始数据[1:系统初始化数据;0:非系统初始化数据]")
    private Whether isSys;

    @ApiModelProperty("创建机构")
    private String createOrgId;

    @ApiModelProperty("创建机构")
    private String createOrgName;

    @ApiModelProperty("描述")
    private String remark;
}