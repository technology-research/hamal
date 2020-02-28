package dev.hamal.entry.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @fileName: DataConsumerConfig.java
 * @description: 消息入口消费者配置
 * @author: by echo huang
 * @date: 2020-02-28 21:35
 */
public class DataConsumerConfig {

    /**
     * rocketMQ参数配置
     *
     * @return {@link RocketMQProperties}
     */
    @Bean
    @ConditionalOnProperty(prefix = "hamal.entry.type", havingValue = "rocketmq", matchIfMissing = true)
    @ConfigurationProperties(prefix = "hamal.entry.consumer.rocketmq")
    public RocketMQProperties rocketMQProperties() {
        return new RocketMQProperties();
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnClass()
    public DefaultMQPushConsumer pushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        return null;
    }
}
