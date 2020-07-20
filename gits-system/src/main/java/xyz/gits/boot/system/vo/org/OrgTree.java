package xyz.gits.boot.system.vo.org;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.gits.boot.common.core.utils.TreeNode;

/**
 * @author songyinyin
 * @date 2020/6/13 下午 04:54
 */
@Data
@ApiModel(value = "机构树")
@EqualsAndHashCode(callSuper = true)
public class OrgTree extends TreeNode<OrgTree> {

    @ApiModelProperty(value = "机构名称")
    private String orgName;
}
