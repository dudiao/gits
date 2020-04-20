package xyz.gits.boot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.gits.boot.system.service.AsyncService;

import java.util.concurrent.CompletableFuture;

/**
 * @author songyinyin
 * @date 2020/3/12 下午 08:27
 */
@Slf4j
@Api(tags = "异步使用示例")
@RestController
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @ApiOperation("异步 无返回值")
    @GetMapping("/open/something")
    public String something() {
        int count = 10;
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            asyncService.doSomething("index = " + i);
        }
        long end = System.currentTimeMillis();
        log.info("do something end, time {} milliseconds", end-start);
        return "success";
    }

    @SneakyThrows
    @ApiOperation("异步 有返回值")
    @GetMapping("/open/somethings")
    public String somethings() {
        CompletableFuture<String> createOrder = asyncService.doSomething1("create order");
        CompletableFuture<String> reduceAccount = asyncService.doSomething2("reduce account");
        CompletableFuture<String> saveLog = asyncService.doSomething3("save log");
        // 等待所有任务都执行完
        CompletableFuture.allOf(createOrder, reduceAccount, saveLog).join();

        // 获取每个任务的返回结果
        String result = createOrder.get() + reduceAccount.get() + saveLog.get();
        return result;
    }

}
