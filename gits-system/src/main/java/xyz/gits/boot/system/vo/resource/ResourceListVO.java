package xyz.gits.boot.system.vo.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 资源 list  对象
 *
 * @author null
 * @date 2020/4/2915:05
 */
@Data
@ApiModel("前端展示资源 list  对象")
public class ResourceListVO {

    @JsonProperty("buttons")
    @ApiModelProperty(value = "资源list")
    private List<ResourceTree> resourceButtons;

    @JsonProperty("resources")
    @ApiModelProperty(value = "按钮list")
    private List<ResourceTree> resources;

    public ResourceListVO() {
    }

    public ResourceListVO(List<ResourceTree> resourceButtons, List<ResourceTree> resources) {
        this.resourceButtons = resourceButtons;
        this.resources = resources;
    }
}
