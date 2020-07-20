package xyz.gits.boot.system.dto.dict;

import xyz.gits.boot.common.core.basic.BaseQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 数据字典-类型传输对象
 *
 * @author null
 * @date 2020/5/26 10:07
 */
@Data
@ApiModel(value = "数据字典-类型查询参数对象")
public class DictTypeQueryDTO extends BaseQueryDTO {

    @ApiModelProperty(value = "字典类型ID")
    @Size(max = 31, message = "字典类型ID不能超过31")
    private String dictTypeId;

    @ApiModelProperty(value = "字典类型代码")
    @Size(max = 127, message = "字典类型代码不能超过127")
    private String code;

    @ApiModelProperty(value = "字典类型名称")
    @Size(max = 255, message = "字典类型名称不能超过255")
    private String name;

    @ApiModelProperty(value = "父级字典类型ID")
    @Size(max = 31, message = "父级字典类型ID不能超过31")
    private String parentId;

}
