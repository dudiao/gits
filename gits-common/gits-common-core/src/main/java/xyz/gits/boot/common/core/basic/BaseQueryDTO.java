package xyz.gits.boot.common.core.basic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import xyz.gits.boot.common.core.constants.SecurityConstant;

import java.io.Serializable;

/**
 * @author songyinyin
 * @date 2018/12/8 16:51
 */
@Data
@Slf4j
public class BaseQueryDTO implements Serializable {

    private static final long serialVersionUID = SecurityConstant.SERIAL_VERSION_UID;

    @ApiModelProperty(value = "页数")
    private Integer currentPage = 1;

    @ApiModelProperty(value = "条数")
    private Integer pageSize = 10;
}
