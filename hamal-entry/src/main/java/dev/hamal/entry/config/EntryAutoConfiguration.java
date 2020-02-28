package dev.hamal.entry.config;

import dev.hamal.entry.data.KafkaDataInput;
import dev.hamal.entry.data.RocketMQDataInput;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @fileName: EntryAutoConfiguration.java
 * @description: 入口自动配置类
 * @author: by echo huang
 * @date: 2020-02-28 21:42
 */
public class EntryAutoConfiguration {
    /**
     * rocketMQ入口开关配置，默认配置
     *
     * @return {@link RocketMQDataInput}
     */
    @Bean
    @ConditionalOnProperty(prefix = "hamal.entry.type", havingValue = "rocketmq", matchIfMissing = true)
    public RocketMQDataInput rocketMQDataInput() {
        return new RocketMQDataInput();
    }

    @Bean
    @ConditionalOnProperty(prefix = "hamal.entry.type",havingValue = "kafka")
    public KafkaDataInput kafkaDataInput(){
        return null;
    }
}
