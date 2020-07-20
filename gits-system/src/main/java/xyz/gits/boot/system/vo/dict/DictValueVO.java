package xyz.gits.boot.system.vo.dict;

import xyz.gits.boot.common.core.basic.BasicVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端数据字典值展示对象vo
 *
 * @author null
 * @date 2020/5/26 10:12
 */
@Data
@ApiModel("前端数据字典值展示对象vo")
public class DictValueVO extends BasicVO {

    @ApiModelProperty(value = "字典值ID")
    private String Id;

    @ApiModelProperty(value = "字典值代码")
    private String code;

    @ApiModelProperty(value = "字典值名称")
    private String name;

    @ApiModelProperty(value = "类型id")
    private String typeId;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;


}
