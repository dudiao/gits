package xyz.gits.boot.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * ID集合对象
 *
 * @author null
 * @date 2020/07/14 15:18
 */
@Data
@ApiModel("ID集合对象")
public class IdList {
    @NotEmpty(message = "ID集合不能为空")
    @ApiModelProperty(value = "ID集合", required = true)
    List<String> ids;
}