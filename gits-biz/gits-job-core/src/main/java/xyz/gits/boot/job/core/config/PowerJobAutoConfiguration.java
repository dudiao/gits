package xyz.gits.boot.job.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author songyinyin
 * @date 2020/7/23 下午 11:33
 */
@Configuration
@EnableConfigurationProperties(PowerJobProperties.class)
public class PowerJobAutoConfiguration {

}
