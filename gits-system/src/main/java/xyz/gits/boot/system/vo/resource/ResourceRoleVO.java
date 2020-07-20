package xyz.gits.boot.system.vo.resource;

import xyz.gits.boot.common.core.utils.TreeNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色资源树
 *
 * @author null
 * @date 2020/5/8 14:18
 */
@Data
@ApiModel("角色资源树")
public class ResourceRoleVO extends TreeNode<ResourceTreeVO> {

    @ApiModelProperty("资源名称")
    @JsonProperty("name")
    private String resourceName;

    @ApiModelProperty("是否勾选")
    @JsonProperty("checked")
    private Boolean checked ;

    @Override
    @JsonProperty("pid")
    public Object getParentId() {
        return super.getParentId();
    }

}