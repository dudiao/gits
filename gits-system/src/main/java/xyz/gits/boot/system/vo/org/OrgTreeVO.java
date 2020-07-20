package xyz.gits.boot.system.vo.org;

import xyz.gits.boot.common.core.utils.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端机构展示对象vo
 *
 * @author null
 * @date 2020/5/26 10:11
 */
@Data
@ApiModel("前端机构展示对象vo")
public class OrgTreeVO extends TreeNode<OrgTreeVO> {

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "机构所在地区代码")
    private String areaCode;

}
