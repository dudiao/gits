package xyz.gits.boot.system.vo.resource;

import xyz.gits.boot.common.core.utils.TreeNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源树结构
 *
 * @author null
 * @date 2020/4/2815:47
 */
@Data
@ApiModel(value = "资源树结构")
public class ResourceTreeVO extends TreeNode<ResourceTreeVO> {

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @Override
    @JsonProperty("resourceId")
    public Object getParentId() {
        return super.getParentId();
    }

}
