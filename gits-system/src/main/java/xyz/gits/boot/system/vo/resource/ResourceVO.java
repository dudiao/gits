package xyz.gits.boot.system.vo.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.api.system.enums.ResourceTarget;
import xyz.gits.boot.api.system.enums.ResourceType;
import xyz.gits.boot.api.system.enums.VisibleStatus;
import xyz.gits.boot.common.core.basic.BasicVO;

/**
 * 资源VO
 *
 * @author null
 * @date 2020/5/14 10:35
 * @since 1.0
 */
@Data
@ApiModel("资源VO")
public class ResourceVO extends BasicVO {

    @ApiModelProperty(value = "资源ID")
    private String resourceId;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "父资源ID")
    private String parentResourceId;
}
