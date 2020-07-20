package xyz.gits.boot.system.dto.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据字典-字典值修改传输对象
 *
 * @author null
 * @date 2020/5/26 10:08
 */
@Data
@ApiModel(value = "数据字典-字典值修改传输对象")
public class DictValueUpdateDTO  implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    @NotBlank(message = "字典ID不能为空")
    @ApiModelProperty(value = "字典ID", required = true)
    @Size(max = 31, message = "字典值ID不能超过32")
    private String id;

    @NotBlank(message = "字典类型ID不能为空")
    @Size(max = 31, message = "字典类型ID不能超过36")
    @ApiModelProperty(value = "字典类型ID", required = true)
    private String typeId;

    @NotBlank(message = "字典值代码不能为空")
    @Size(max = 255, message = "字典值代码不能超过255")
    @ApiModelProperty(value = "字典值代码", required = true)
    private String code;

    @NotBlank(message = "字典值名称不能为空")
    @Size(max = 255, message = "字典值名称不能超过255")
    @ApiModelProperty(value = "字典值名称", required = true)
    private String name;

    @ApiModelProperty(value = "排序(DESC)")
    @Max(value = 9999, message = "排序不能大于9999")
    private Integer orderNum;


}
