package xyz.gits.boot.system.controller;


import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.dto.OrgDTO;
import xyz.gits.boot.system.entity.Org;
import xyz.gits.boot.api.system.vo.OrgTree;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.system.service.IOrgService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 机构
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Slf4j
@RestController
@Api(value = "org", tags = "机构管理模块")
public class OrgController extends BasicController {

    @Autowired
    private IOrgService orgService;

    @GetMapping("/system/org/detail")
    @ApiOperation(value = "机构详情")
    public RestResponse<Org> detail(@NotBlank(message = "机构id不能为空") @ApiParam(name = "orgId", value = "机构id")
                                    @RequestParam("orgId") String orgId) {
        Org org = orgService.getById(orgId);
        if (ObjectUtil.isEmpty(org)) {
            log.info("[机构管理 - 机构详情] 机构不存在，orgId={}", orgId);
            RestResponse.build(ResponseCode.ORG_NOT_EXIST);
        }
        return RestResponse.success(org);
    }

    @GetMapping("/system/org/tree")
    @ApiOperation(value = "机构树-名称&id")
    public RestResponse<List<OrgTree>> tree() {
        List<OrgTree> orgTree = orgService.getOrgTree();
        return RestResponse.success(orgTree);
    }

    @PostMapping("/system/org/save")
    @ApiOperation(value = "新增机构")
    public RestResponse save(@Valid @RequestBody OrgDTO dto) {
        orgService.saveOrg(dto);
        return RestResponse.success();
    }

    @PutMapping("/system/org/update")
    @ApiOperation(value = "修改机构")
    public RestResponse update(@Valid @RequestBody OrgDTO dto) {
        orgService.updateOrg(dto);
        return RestResponse.success();
    }

    @DeleteMapping("/system/org/delete")
    @ApiOperation(value = "删除机构")
    public RestResponse delete(@NotBlank(message = "机构Id不能为空")
                               @Size(max = 16, message = "机构代码不能超过16") String orgId) {
        orgService.delete(orgId);
        return RestResponse.success();
    }
}

