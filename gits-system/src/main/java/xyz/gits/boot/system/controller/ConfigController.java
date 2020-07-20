package xyz.gits.boot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.gits.boot.api.system.utils.ConvertUtils;
import xyz.gits.boot.common.core.basic.BasicController;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.response.TableResponse;
import xyz.gits.boot.common.core.utils.BeanUtils;
import xyz.gits.boot.system.dto.config.ConfigDTO;
import xyz.gits.boot.system.dto.config.ConfigQueryDTO;
import xyz.gits.boot.system.entity.Config;
import xyz.gits.boot.system.service.IConfigService;
import xyz.gits.boot.system.vo.config.ConfigVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 参数配置 前端控制器
 * </p>
 *
 * @author null
 * @since 2020-05-20
 */
@RestController
@Api(tags = "配置管理中心")
public class ConfigController extends BasicController {

    @Autowired
    private IConfigService configService;

    @PostMapping("/system/config/save")
    @ApiOperation(value = "新增配置")
    public RestResponse save(@Validated @RequestBody ConfigDTO configAddDTO) {
        configService.save(configAddDTO);
        return RestResponse.success();
    }

    @PutMapping("/system/config/update")
    @ApiOperation(value = "修改配置")
    public RestResponse update(@Validated @RequestBody ConfigDTO configDTO) {
        configService.update(configDTO);
        return RestResponse.success();
    }

    @GetMapping("/system/config/detail")
    @ApiOperation(value = "根据配置ID查询配置详情")
    public RestResponse<ConfigVO> detail(@ApiParam(name = "configKey", value = "配置Id", required = true) @RequestParam("configKey") String configKey) {
        ConfigVO config = configService.find(configKey);
        return RestResponse.success(config);
    }

    @DeleteMapping("/system/config/delete")
    @ApiOperation(value = "删除配置")
    public RestResponse delete(@ApiParam(name = "configKey", value = "配置Id", required = true) @RequestParam("configKey") String configKey) {
        configService.delete(configKey);
        return RestResponse.success();
    }

    @GetMapping("/system/config/page")
    @ApiOperation(value = "分页配置信息")
    public TableResponse<ConfigVO> page(@Validated ConfigQueryDTO configDTO) {
        IPage<Config> page = configService.getPage(configDTO);
        List<ConfigVO> collect = page.getRecords().stream().map(e -> {
            ConfigVO configVO = new ConfigVO();
            BeanUtils.copyPropertiesIgnoreNull(e, configVO);
            configVO.setConfigType(e.getConfigType());
            return configVO;
        }).collect(Collectors.toList());

        ConvertUtils.convertUserList(collect);
        return TableResponse.success(page.getTotal(), collect);
    }

}

