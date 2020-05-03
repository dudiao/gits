package xyz.gits.cloud.common.sentinel;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author songyinyin
 * @date 2020/4/30 下午 11:37
 */
@Configuration
@ConditionalOnBean(NacosConfigProperties.class)
public class SentinelAutoConfiguration {

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    // nacos group
    private static final String groupId = "Gits_Sentinel";
    // nacos flow rule dataId
    private static final String flowDataId = "xyz.gits.cloud.common.sentinel.flow.rule";
    // nacos degrade rule dataId
    private static final String degradeDataId = "xyz.gits.cloud.common.sentinel.degrade.rule";
    // nacos system rule dataId
    private static final String systemDataId = "xyz.gits.cloud.common.sentinel.system.rule";
    // nacos authority rule dataId
    private static final String authorityDataId = "xyz.gits.cloud.common.sentinel.authority.rule";


    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler() {
        return new GitsUrlBlockHandler();
    }

    @PostConstruct
    public void init() {

        // 持久化流控规则
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(nacosConfigProperties.getServerAddr(), groupId, flowDataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());

        // 持久化降级规则
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(nacosConfigProperties.getServerAddr(), groupId, degradeDataId,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());

        // 持久化系统规则
        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new NacosDataSource<>(nacosConfigProperties.getServerAddr(), groupId, systemDataId,
                source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {}));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());

        // 持久化授权规则
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource = new NacosDataSource<>(nacosConfigProperties.getServerAddr(), groupId, authorityDataId,
                source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {}));
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());
    }
}
