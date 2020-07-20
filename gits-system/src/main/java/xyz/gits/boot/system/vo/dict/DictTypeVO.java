package xyz.gits.boot.system.vo.dict;

import xyz.gits.boot.common.core.basic.BasicVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端数据字典类型展示对象vo
 *
 * @author null
 * @date 2020/5/26 10:11
 */
@Data
@ApiModel("前端数据字典类型展示对象vo")
public class DictTypeVO  extends BasicVO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "父节点")
    private String parentId;

    @ApiModelProperty(value = "字典类型代码")
    private String code;

    @ApiModelProperty(value = "字典类型名称")
    private String name;


}
