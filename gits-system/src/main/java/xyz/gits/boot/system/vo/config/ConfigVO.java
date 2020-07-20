package xyz.gits.boot.system.vo.config;

import xyz.gits.boot.api.system.enums.ConfigType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.common.core.basic.BasicVO;

/**
 * 前端配置展示对象
 *
 * @author null
 * @date 2020/5/21 9:25
 * @since 1.0
 */
@Data
@ApiModel("前端配置展示对象")
public class ConfigVO extends BasicVO {

    @ApiModelProperty(value = "配置KEY")
    private String configKey;

    @ApiModelProperty(value = "配置名称")
    private String configName;

    @ApiModelProperty(value = "配置组")
    private String configGroup;

    @ApiModelProperty(value = "配置项类型")
    private ConfigType configType;

    @ApiModelProperty(value = "配置项Values值")
    private String configValues;

    @ApiModelProperty(value = "配置项默认Values值")
    private String configDefaultValue;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    @ApiModelProperty(value = "备注说明")
    private String remark;

}
