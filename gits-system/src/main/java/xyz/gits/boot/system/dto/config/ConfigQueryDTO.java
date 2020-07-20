package xyz.gits.boot.system.dto.config;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.common.core.basic.BaseQueryDTO;

import javax.validation.constraints.Size;

/**
 * 配置传输对象
 *
 * @author null
 * @date 2020/6/12 9:38
 */
@Data
@ApiModel("配置查询DTO")
public class ConfigQueryDTO extends BaseQueryDTO {

    @ApiModelProperty(value = "配置KEY")
    @Size(max = 63, message = "配置KEY不能超过63")
    private String configId;

    @ApiModelProperty(value = "配置名称")
    @Size(max = 63, message = "配置名称不能超过63")
    private String configName;

    @ApiModelProperty(value = "配置组")
    @Size(max = 11, message = "配置组不能超过11")
    private String configGroup;

    @ApiModelProperty(value = "配置类型[SYSTEM:系统设置;BUSINESS:业务设置]")
    @Size(max = 31, message = "31")
    private String configType;

}
