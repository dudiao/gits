package xyz.gits.boot.single.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.jmx.annotation.JmxEndpoint;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

/**
 * @author songyinyin
 * @date 2020/3/12 下午 07:23
 */
@JmxEndpoint(id = "gitsExecutor")
public class ExecutorEndpoint {

    @Resource
    private ThreadPoolTaskExecutor doSomethingExecutor;

    @WriteOperation
    public Map<String, String> update(int maxPoolSize) {
        doSomethingExecutor.setMaxPoolSize(maxPoolSize);
        return Collections
                .unmodifiableMap(Collections.singletonMap("message", "maxPoolSize is changed to " + maxPoolSize));
    }

}
