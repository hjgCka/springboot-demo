package com.hjg.scheduler.demo.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/7
 */
@Component
public class MyTask {

    private DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //这里的表达式支持@Value或SpEL
    @Scheduled(cron = "0 * 9-21 * * ?")
    public void printThread() {
        String threadName = Thread.currentThread().getName();
        LocalDateTime now = LocalDateTime.now();
        String dateTimeStr = df.format(now);

        System.out.println("Thread : " + threadName + ", dateTimeStr = " + dateTimeStr);
    }

    @Async("myExecutor")
    public CompletableFuture<String> asyncInvocate(LocalDateTime dateTime) {
        return CompletableFuture.supplyAsync(() -> {
            String dateTimeStr = df.format(dateTime);
            return dateTimeStr;
        });
    }
}
