package xyz.gits.cloud.common.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author songyinyin
 * @date 2020/4/30 下午 11:37
 */
@Configuration
public class SentinelAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler() {
        return new GitsUrlBlockHandler();
    }
}
