package xyz.gits.boot.common.core.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.gits.boot.common.core.exception.handler.GlobalExceptionHandler;

/**
 * @author songyinyin
 * @date 2020/4/27 上午 11:53
 */
@Configuration
@EnableConfigurationProperties(GitsProperties.class)
public class GitsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
