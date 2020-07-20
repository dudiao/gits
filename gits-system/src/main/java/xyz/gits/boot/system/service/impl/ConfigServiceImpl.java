package xyz.gits.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.gits.boot.api.system.enums.ConfigType;
import xyz.gits.boot.api.system.utils.AuthUtils;
import xyz.gits.boot.api.system.utils.ConvertUtils;
import xyz.gits.boot.common.core.basic.BasicServiceImpl;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.system.dto.config.ConfigDTO;
import xyz.gits.boot.system.dto.config.ConfigQueryDTO;
import xyz.gits.boot.system.entity.Config;
import xyz.gits.boot.system.mapper.ConfigMapper;
import xyz.gits.boot.system.service.IConfigService;
import xyz.gits.boot.system.vo.config.ConfigVO;

import java.time.LocalDateTime;

/**
 * 配置中心 服务接口类默认实现
 *
 * @author songyinyin
 * @since 2020-05-20
 */
@Slf4j
public class ConfigServiceImpl extends BasicServiceImpl<ConfigMapper, Config> implements IConfigService {

    @Autowired
    private ConfigMapper configMapper;

    /**
     * 新增配置
     *
     * @param configDTO 配置传输对象
     * @author null
     * @date 2020/5/25 17:37
     */
    @Override
    public void save(ConfigDTO configDTO) {
        Config configInDb = configMapper.selectById(configDTO.getConfigId());
        if (BeanUtil.isNotEmpty(configInDb)) {
            log.warn("[配置管理-新增配置信息]-配置已存在,配置key:ConfigId={}", configDTO.getConfigId());
            throw new SystemNoLogException(ResponseCode.CONFIG_IS_EXIST);
        }
        Config config = new Config();
        BeanUtil.copyProperties(configDTO, config);
        config.setConfigType(ConfigType.valueOf(configDTO.getConfigType()));
        LocalDateTime now = LocalDateTime.now();
        config.setCreateTime(now);
        config.setCreateUserId(AuthUtils.getUserId());
        baseMapper.insert(config);
    }

    /**
     * 根据配置id删除
     *
     * @param configId 配置ID
     * @author null
     * @date 2020/5/25 17:37
     */
    @Override
    public void delete(String configId) {
        Config config = configMapper.selectById(configId);
        if (BeanUtil.isEmpty(config)) {
            log.warn("[配置管理-删除配置信息]-配置不存在,配置key:configId={}", configId);
            throw new SystemNoLogException(ResponseCode.CONFIG_IS_NOT_EXIST);
        }

        if (config.getConfigType().equals(ConfigType.SYSTEM)) {
            log.warn("[配置管理-删除配置信息]-系统配置不允许删除,配置Id:configId={},配置类型:configType={}", config.getConfigId(), config.getConfigType());
            throw new SystemNoLogException(ResponseCode.CONFIG_IS_NOT_DELETE);
        }
        configMapper.deleteById(configId);
    }

    /**
     * 修改配置
     *
     * @param configDTO 配置传输对象
     * @author null
     * @date 2020/5/26 18:43
     */
    @Override
    public void update(ConfigDTO configDTO) {

        Config config = configMapper.selectById(configDTO.getConfigId());
        if (BeanUtil.isEmpty(config)) {
            log.warn("[配置管理-修改配置信息]-配置不存在,配置key:configId={},配置名:ConfigName={}", configDTO.getConfigId(), configDTO.getConfigName());
            throw new SystemNoLogException(ResponseCode.CONFIG_ID_IS_NOT_EXIST);

        }
        config.setUpdateUserId(AuthUtils.getUserId());
        config.setUpdateTime(LocalDateTime.now());
        //如果类型是SYSTEM 只可以修改ConfigValues
        if (config.getConfigType().equals(ConfigType.SYSTEM)) {
            log.info("[配置管理-修改配置信息]-系统配置只允许修改values值,配置key:configId={},配置类型:ConfigType={}", configDTO.getConfigId(), configDTO.getConfigType());
            config.setConfigValues(configDTO.getConfigValues());
            config.setOrderNum(configDTO.getOrderNum());
            configMapper.updateById(config);
        }
        //如果类型是BUSINESS 可修改除配置类型外的其他内容
        if (config.getConfigType().equals(ConfigType.BUSINESS)) {
            log.info("[配置管理-修改配置信息]-业务配置可修改除配置类型外的其他内容,配置key:configId={},配置类型:ConfigType={}", configDTO.getConfigId(), configDTO.getConfigType());
            BeanUtil.copyProperties(configDTO, config);
            configMapper.updateById(config);
        }
    }

    /**
     * 根据配置id查询
     *
     * @param configId 配置ID
     * @author null
     * @date 2020/5/27 15:51
     */
    @Override
    public ConfigVO find(String configId) {
        Config config = configMapper.selectById(configId);
        if (BeanUtil.isEmpty(config)) {
            log.warn("[配置管理-查找配置信息失败]-配置的信息为空,配置key:configId={}", configId);
            throw new SystemNoLogException(ResponseCode.CONFIG_ID_IS_NOT_EXIST);
        }
        ConfigVO configVO = new ConfigVO();
        BeanUtil.copyProperties(config, configVO);
        ConvertUtils.convertUser(configVO);
        return configVO;
    }

    /**
     * 查询所有配置
     *
     * @param configDTO 配置传输对象
     * @return 前端配置展示对象 {@link Config}
     * @author null
     * @date 2020/5/25 17:38
     */
    @Override
    public IPage<Config> getPage(ConfigQueryDTO configDTO) {

        QueryWrapper<Config> queryWrapper = parseParameter();
        queryWrapper.lambda()
                .eq(StrUtil.isNotEmpty(configDTO.getConfigGroup()), Config::getConfigGroup, configDTO.getConfigGroup())
                .like(StrUtil.isNotEmpty(configDTO.getConfigId()), Config::getConfigId, configDTO.getConfigId())
                .like(StrUtil.isNotEmpty(configDTO.getConfigName()), Config::getConfigName, configDTO.getConfigName());

        return page(queryWrapper).setCurrent(configDTO.getCurrentPage()).setSize(configDTO.getPageSize());
    }


}
