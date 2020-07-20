package xyz.gits.boot.system.dto.dict;

import xyz.gits.boot.common.core.basic.BaseQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 数据字典-字典值传输对象
 *
 * @author null
 * @date 2020/5/26 10:08
 */
@Data
@ApiModel(value = "数据字典-字典值传输对象")
public class DictValueQueryDTO extends BaseQueryDTO {

    @ApiModelProperty(value = "字典ID")
    @NotBlank(message = "字典值ID不能为空")
    @Size(max = 31, message = "字典值ID不能超过32")
    private String id;

    @ApiModelProperty(value = "字典类型ID")
    @Size(max = 31, message = "字典类型ID不能超过36")
    private String typeId;

    @ApiModelProperty(value = "字典值代码")
    @Size(max = 255, message = "字典值代码不能超过255")
    private String code;

    @ApiModelProperty(value = "字典值名称")
    @Size(max = 255, message = "字典值名称不能超过255")
    private String name;

    @ApiModelProperty(value = "排序(DESC)")
    @Max(value = 9999, message = "排序不能大于9999")
    private Integer orderNum;


}
