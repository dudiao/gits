package xyz.gits.boot.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Gits系统配置项
 *
 * @author songyinyin
 * @date 2020/5/31 下午 02:25
 */
@Data
@ConfigurationProperties(prefix = "gits")
public class GitsProperties {

    /**
     * 安全配置项
     */
    @NestedConfigurationProperty
    private Security security = new Security();

    /**
     * 安全配置项
     */
    @Data
    public static class Security {

        /**
         * 是否开启验证码，默认为 true，开启
         */
        private boolean verifyCodeEnable = true;
        /**
         * 验证码过期时间，单位：秒，默认120秒
         */
        private long codeExpireTime = 120;
    }

}
