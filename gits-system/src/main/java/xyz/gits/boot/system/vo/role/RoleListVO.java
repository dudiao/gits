package xyz.gits.boot.system.vo.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色list对象
 *
 * @author null
 * @date 2020/5/15 15:07
 * @since 1.0
 */
@Data
@ApiModel("角色list对象")
public class RoleListVO {

    @JsonProperty("userRoleList")
    @ApiModelProperty(value = "用户角色集合")
    private List<RoleVO> userRoleList;

    @JsonProperty("allRoleList")
    @ApiModelProperty(value = "所有角色集合")
    private List<RoleVO> allRoleList;

    public RoleListVO() {
    }

    public RoleListVO(List<RoleVO> userRoleList, List<RoleVO> allRoleList) {
        this.userRoleList = userRoleList;
        this.allRoleList = allRoleList;
    }
}
