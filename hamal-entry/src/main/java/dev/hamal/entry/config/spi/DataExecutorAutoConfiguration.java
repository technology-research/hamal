package dev.hamal.entry.config.spi;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import dev.hamal.entry.config.properties.DataInputThreadPoolProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @fileName: DataExecutorAutoConfiguration.java
 * @description: 数据入口执行器自动配置
 * @author: by echo huang
 * @date: 2020-02-29 16:32
 */
@Configuration
@EnableConfigurationProperties(DataInputThreadPoolProperties.class)
public class DataExecutorAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "hamal.entry.input.pool")
    public DataInputThreadPoolProperties dataInputThreadPoolProperties() {
        return new DataInputThreadPoolProperties();
    }

    @Bean(name = "dataExecutor",destroyMethod = "shutdown")
    @ConditionalOnClass(DataInputThreadPoolProperties.class)
    public ExecutorService dataExecutor(@Qualifier("dataInputThreadPoolProperties") DataInputThreadPoolProperties properties) {
        return new ThreadPoolExecutor(properties.getCorePoolSize(), properties.getMaximumPoolSize(),
                properties.getKeepAliveTime(),
                properties.chooseTimeUnit(),
                new ArrayBlockingQueue<>(properties.getWorkQueueSize()),
                new ThreadFactoryBuilder().setNameFormat(properties.getThreadNamePrefix() + "-%d").build(),
                properties.chooseRejectedStrategy());
    }
}
