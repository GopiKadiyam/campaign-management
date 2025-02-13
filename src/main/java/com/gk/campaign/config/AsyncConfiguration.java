package com.gk.campaign.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfiguration {

    @Bean(name = "lightTaskExecutor")
    public Executor taskExecutorForLightTasks(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(2000000);
        executor.setThreadNamePrefix("LightTaskExecThread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.setRejectedExecutionHandler((r,executor1)->log.warn("Task Rejected, thread pool is full and queue is also full"));
        executor.initialize();
        return executor;
    }

//    @Bean(name = "heavyTaskExecutor")
//    public Executor taskExecutorForHeavyTasks(){
//        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(3);
//        executor.setMaxPoolSize(3);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("HeavyTaskExecThread-");
//        executor.setRejectedExecutionHandler((r,executor1)->log.warn("Task Rejected, thread pool is full and queue is also full"));
//        executor.initialize();
//        return executor;
//    }
}
