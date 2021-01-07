package com.hjg.scheduler.demo;

import com.hjg.scheduler.demo.task.MyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/7
 */
@SpringBootApplication
public class SchedulerApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MyTask task = applicationContext.getBean(MyTask.class);
        CompletableFuture<String> result = task.asyncInvocate(LocalDateTime.now());
        result.thenAccept(str -> System.out.println("asyncInvocate = " + str));
    }
}
