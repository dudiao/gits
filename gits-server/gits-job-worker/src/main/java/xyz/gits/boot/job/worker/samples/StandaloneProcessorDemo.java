package xyz.gits.boot.job.worker.samples;

import com.github.kfcfans.powerjob.worker.core.processor.ProcessResult;
import com.github.kfcfans.powerjob.worker.core.processor.TaskContext;
import com.github.kfcfans.powerjob.worker.core.processor.sdk.BasicProcessor;
import com.github.kfcfans.powerjob.worker.log.OmsLogger;
import org.springframework.stereotype.Component;

/**
 * 单机处理器 示例
 *
 * @author songyinyin
 * @date 2020/7/24 上午 12:05
 */
@Component
public class StandaloneProcessorDemo implements BasicProcessor {

    @Override
    public ProcessResult process(TaskContext taskContext) throws Exception {

        // 获取日志对象，该日志会输出到前端页面
        OmsLogger omsLogger = taskContext.getOmsLogger();
        omsLogger.info("StandaloneProcessorDemo 开始执行，context = {}.", taskContext);

        return new ProcessResult(true, "执行成功");
    }
}
