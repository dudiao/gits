package xyz.gits.boot.system.vo.dict;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.common.core.utils.TreeNode;

/**
 * 前端数据字典类型展示对象vo
 *
 * @author null
 * @date 2020/7/7 10:11
 */
@Data
@ApiModel("树结构字典类型VO")
public class DictTypeTreeVO extends TreeNode<DictTypeTreeVO> {

    @ApiModelProperty(value = "字典类型代码")
    private String code;

    @ApiModelProperty(value = "字典类型名称")
    private String name;

}
