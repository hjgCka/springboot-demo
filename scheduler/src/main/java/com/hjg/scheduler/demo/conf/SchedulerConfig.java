package com.hjg.scheduler.demo.conf;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/7
 */
@Service
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //手动注册调度任务
    }
}
