package dev.hamal.entry.config.spi;

import dev.hamal.entry.config.properties.RocketMQProperties;
import dev.hamal.entry.input.DataInput;
import dev.hamal.entry.input.DefaultDataInput;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @fileName: EntryAutoConfiguration.java
 * @description: 数据入口自动配置类
 * @author: by echo huang
 * @date: 2020-02-28 21:42
 */
@Configuration
@AutoConfigureAfter(value = {DataConsumerAutoConfiguration.class, DataExecutorAutoConfiguration.class})
public class DataEntryAutoConfiguration {

    /**
     * rocketMQ入口开关配置，默认配置
     *
     * @return {@link DefaultDataInput}
     */
    @Bean
    @ConditionalOnProperty(prefix = "hamal.entry.type", havingValue = "rocketmq", matchIfMissing = true)
    public DataInput<String> defaultDataInput(@Qualifier("pushConsumer") DefaultMQPushConsumer consumer, @Qualifier("rocketMqProperties") RocketMQProperties properties) {
        return new DefaultDataInput(consumer, properties.getTopic());
    }

    /**
     * kafka入口配置开关，默认配置
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "hamal.entry.type", havingValue = "kafka")
    public DataInput<String> kafkaDataInput() {
        return null;
    }
}
