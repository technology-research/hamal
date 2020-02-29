package dev.hamal.entry.config.spi;

import dev.hamal.common.utils.BeanCopierUtils;
import dev.hamal.entry.config.properties.RocketMQProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @fileName: DataConsumerAutoConfiguration.java
 * @description: 消息入口消费者配置
 * @author: by echo huang
 * @date: 2020-02-28 21:35
 */
@EnableConfigurationProperties(RocketMQProperties.class)
@Configuration
public class DataConsumerAutoConfiguration {

    /**
     * rocketMQ参数配置
     *
     * @return {@link RocketMQProperties}
     */
    @Bean
    @ConditionalOnProperty(prefix = "hamal.entry.type", havingValue = "rocketmq", matchIfMissing = true)
    @ConfigurationProperties(prefix = "hamal.entry.consumer.rocketmq")
    public RocketMQProperties rocketMqProperties() {
        return new RocketMQProperties();
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnClass(RocketMQProperties.class)
    public DefaultMQPushConsumer pushConsumer(@Qualifier("rocketMqProperties") RocketMQProperties properties) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        BeanCopierUtils.copy(properties, consumer);
        //默认为集群消费方式
        consumer.setMessageModel(properties.chooseMessageModel()
                .orElse(MessageModel.CLUSTERING));
        //默认为从最后offset开始消费
        consumer.setConsumeFromWhere(properties.chooseConsumeFromWhere()
                .orElse(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET));
        return consumer;
    }


}
