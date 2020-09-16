package xyz.gits.boot.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.enums.StopStatus;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.constants.ConfigConstants;
import xyz.gits.boot.common.core.excel.ExcelUtil;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.dto.org.OrgDTO;
import xyz.gits.boot.system.entity.Config;
import xyz.gits.boot.system.entity.Org;
import xyz.gits.boot.system.service.IConfigService;
import xyz.gits.boot.system.service.IOrgService;
import xyz.gits.boot.system.vo.org.OrgExcelVO;
import xyz.gits.boot.system.vo.org.OrgTree;
import xyz.gits.boot.system.vo.org.OrgVO;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IConfigService configService;

    @GetMapping("/system/org/detail")
    @ApiOperation(value = "机构详情")
    public RestResponse<OrgVO> findById(@RequestParam(value = "orgId") @ApiParam(name = "orgId", value = "机构Id", required = true) String orgId) {
        OrgVO orgVO = orgService.findById(orgId);
        return RestResponse.success(orgVO);
    }

    @GetMapping("/system/org/tree")
    @ApiOperation(value = "机构树-名称&id")
    public RestResponse<List<OrgTree>> tree(@ApiParam(name = "stopStatus", value = "启用停用标志[0:启用;1:停用;a:所有]", required = true)
                                            @RequestParam("stopStatus") String stopStatus) {
        List<OrgTree> orgTree = orgService.getOrgTree(stopStatus);
        return RestResponse.success(orgTree);
    }

    @PostMapping("/system/org/save")
    @ApiOperation(value = "新增机构")
    public RestResponse save(@Valid @RequestBody OrgDTO dto) {
        orgService.addOrg(dto);
        return RestResponse.success();
    }

    @PutMapping("/system/org/update")
    @ApiOperation(value = "修改机构")
    public RestResponse update(@Valid @RequestBody OrgDTO dto) {
        orgService.updateOrg(dto);
        return RestResponse.success();
    }

    @GetMapping("/system/org/status")
    @ApiOperation("修改启用停用标志")
    public RestResponse<Org> updateRoleStatus(@ApiParam(name = "orgId", value = "机构Id", required = true) @RequestParam("orgId") String orgId,
                                              @ApiParam(name = "stopStatus", value = "启用停用标志[0:启用;1:停用]", required = true) @RequestParam("stopStatus") String stopStatusCode,
                                              @ApiParam(name = "stopReason", value = "停用原因", required = true) @RequestParam("stopReason") String stopReason) {
        orgService.updateOrgStatus(orgId, StopStatus.fromString(stopStatusCode), stopReason);
        return RestResponse.success();
    }

    @GetMapping("/system/org/export")
    @ApiOperation("导出机构信息")
    public RestResponse exportOrg() {
        QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(Org::getOrderNum);
        List<Org> list = orgService.list(queryWrapper);
        List<OrgExcelVO> collect = list.stream().map(e -> {
            OrgExcelVO orgExcelVO = new OrgExcelVO();
            BeanUtils.copyPropertiesIgnoreNull(e, orgExcelVO);
            return orgExcelVO;
        }).collect(Collectors.toList());
        Config config = configService.getById(ConfigConstants.SYSTEM_PATH);
        String path = config.getConfigValues();
        ExcelUtil<OrgExcelVO> util = new ExcelUtil<>(OrgExcelVO.class, path);
        return RestResponse.success(util.exportForExcel(collect, "机构信息"));
    }

}

