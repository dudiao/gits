package xyz.gits.boot.job.core.config;

import cn.hutool.core.util.StrUtil;
import com.github.kfcfans.powerjob.worker.OhMyWorker;
import com.github.kfcfans.powerjob.worker.common.OhMyConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * PowerJob 自动装配
 *
 * @author songyinyin
 * @date 2020/7/23 下午 11:33
 */
@Configuration
@EnableConfigurationProperties(PowerJobProperties.class)
public class PowerJobAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OhMyWorker initPowerJob(PowerJobProperties powerJobProperties) {

        // 服务器HTTP地址（端口号为 server.port，而不是 ActorSystem port），请勿添加任何前缀（http://）
        List<String> serverAddress = StrUtil.isBlank(powerJobProperties.getServerAddress())
            ? Collections.singletonList("127.0.0.1:7700")
            : Arrays.asList(powerJobProperties.getServerAddress().split(","));

        // 1. 创建配置文件
        OhMyConfig config = new OhMyConfig();
        // 可以不显示设置，默认值 27777
        config.setPort(powerJobProperties.getAkkaPort());
        // appName，需要提前在控制台注册，否则启动报错
        config.setAppName(powerJobProperties.getAppName());
        config.setServerAddress(serverAddress);
        // 如果没有大型 Map/MapReduce 的需求，建议使用内存来加速计算
        // 有大型 Map/MapReduce 需求，可能产生大量子任务（Task）的场景，请使用 DISK，否则妥妥的 OutOfMemeory
        config.setStoreStrategy(powerJobProperties.getStoreStrategy());

        // 2. 创建 Worker 对象，设置配置文件
        OhMyWorker ohMyWorker = new OhMyWorker();
        ohMyWorker.setConfig(config);
        return ohMyWorker;
    }
}
