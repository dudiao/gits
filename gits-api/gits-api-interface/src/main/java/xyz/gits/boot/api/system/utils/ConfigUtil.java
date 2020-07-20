package xyz.gits.boot.api.system.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import xyz.gits.boot.api.system.dto.config.ConfigDTO;

/**
 * <p>
 * 配置中心工具类
 * </p>
 *
 * @author null
 * @date 2020/5/25 18:06
 */
@Slf4j
public class ConfigUtil {

//    private static IConfigUtilService configUtilService = SpringContextHolder.getBean(IConfigUtilService.class);

    /**
     * 获取配置中心的参数配置
     *
     * @param configKey 配置参数KEY
     * @return 若不存在 configKey 或者不传，返回null；否则返回 {@link ConfigDTO}前端展示配置信息
     * @author null
     * @date 2020/5/26 18:07
     */
    public static ConfigDTO getConfig(String configKey) {
//        return configUtilService.getConfig(configKey);
        return null;
    }

    /**
     * Integer 转 String
     *
     * @param configKey 配置Key
     * @return {@link String}
     * @author null
     * @date 2020/5/29 15:26
     */
    public static String getString(String configKey) {
        if (configKey == null) {
            return null;
        }
        ConfigDTO configDTO = getConfig(configKey);
        //判断配置参数 value 值 是否存在？注入默认值；原值
        if (StrUtil.isNotBlank(configDTO.getConfigValues())) {
            return configDTO.getConfigValues();
        }
        log.info("因为配置参数{} 的 value:{}值为空;所以取它的默认 value 值{}", configKey, configDTO.getConfigValues(), configDTO.getConfigDefaultValue());

        if (StrUtil.isNotBlank(configDTO.getConfigDefaultValue())) {
            return configDTO.getConfigDefaultValue();
        }
        log.info("因为配置参数{} 的 value:{}值为空;所以取它的默认 value 值{}", configKey, configDTO.getConfigValues(), configDTO.getConfigDefaultValue());

        //value 值为空;默认值也为空
        return "";
    }

    /**
     * String 转 Integer
     *
     * @param configKey 配置Key
     * @return {@link Integer}
     * @author null
     * @date 2020/5/29 15:26
     */
    public static Integer getInteger(String configKey) {
        if (configKey == null) {
            return null;
        }
        Integer result = null;
        ConfigDTO configDTO = getConfig(configKey);
        //value 值不为空
        if (StrUtil.isNotBlank(configDTO.getConfigValues())) {
            result = Integer.valueOf(configDTO.getConfigValues());
            return result;
        }
        log.info("因为配置参数{} 的 value:{}值为空;所以取它的默认 value 值{}", configKey, configDTO.getConfigValues(), configDTO.getConfigDefaultValue());
        //value 值为空，读取默认值
        if (StrUtil.isNotBlank(configDTO.getConfigDefaultValue())) {
            result = Integer.valueOf(configDTO.getConfigDefaultValue());
            return result;
        }
        log.info("因为配置参数{} 的 value:{}值为空;所以取它的默认 value 值{}", configKey, configDTO.getConfigValues(), configDTO.getConfigDefaultValue());
        //value 值为空;默认值也为空
        return null;
    }
}
