package mts.parser.config.thread.pool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ThreadPoolConfig {

    private final ThreadPoolTaskExecutorProperties threadPoolTaskExecutorProperties;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadPoolTaskExecutorProperties.corePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(threadPoolTaskExecutorProperties.maxPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(threadPoolTaskExecutorProperties.queueCapacity());
        threadPoolTaskExecutor.setThreadNamePrefix(threadPoolTaskExecutorProperties.threadNamePrefix());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
