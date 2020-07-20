package xyz.gits.boot.system.vo.group;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.gits.boot.api.system.vo.UserDetailsVO;
import xyz.gits.boot.system.vo.org.OrgVO;

import java.io.Serializable;
import java.util.List;

/**
 * 前端数据字典类型展示对象vo
 *
 * @author null
 * @date 2020/5/26 10:11
 */
@Data
@ApiModel("前端数据组展示对象vo")
public class GroupDetailVO implements Serializable {

    @ApiModelProperty(value = "数据组VO对象")
    private GroupVO groupVO;

    @ApiModelProperty(value = "机构VO信息")
    private List<OrgVO> listOrg;

    @ApiModelProperty(value = "用户VO信息")
    private List<UserDetailsVO> listUser;
}
