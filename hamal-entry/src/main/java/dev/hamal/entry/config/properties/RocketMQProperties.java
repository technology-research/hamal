package dev.hamal.entry.config.properties;

import lombok.Data;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.Optional;

/**
 * @fileName: RocketMQProperties.java
 * @description: rocketMQ参数配置
 * @author: by echo huang
 * @date: 2020-02-28 21:41
 */
@Data
public class RocketMQProperties {
    /**
     * namesrvAddr地址 默认为:localhost
     */
    private String namesrvAddr = "localhost";
    /**
     * 订阅topic 默认为:Hamal_Binlog
     */
    private String topic = "Hamal_Binlog";
    ;
    /**
     * 消费者组 默认为:Hamal_Consumer_Group
     */
    private String consumeGroup = "Hamal_Consumer_Group";
    ;
    /**
     * 消费最小线程数 默认为:30
     */
    private int consumeThreadMin = 30;
    ;
    /**
     * 消费最大线程数 默认为:30
     */
    private int consumeThreadMax = 30;
    ;
    /**
     * 消费模型 默认为:集群
     * 支持:BROADCASTING,CLUSTERING
     *
     * @see MessageModel
     */
    private String messageModel = MessageModel.CLUSTERING.getModeCN();
    ;
    /**
     * 本地缓存最大消息数量超过后本地消费流控 默认为:2000
     */
    private int pullThresholdForQueue = 2000;
    ;
    /**
     * 消息队列最大缓存的大小单位MB 默认为:200MB
     */
    private int pullThresholdSizeForQueue = 200;
    /**
     * 每次最多拉取的消息个数 默认为:32
     */
    private int pullBatchSize = 32;

    /**
     * 从哪里开始消费 默认为:从后的offset开始消费
     *
     * @see ConsumeFromWhere
     */
    private String consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET.name();

    /**
     * 选择消费方式
     *
     * @return {@link DefaultMQPushConsumer#getConsumeFromWhere()}
     */
    public Optional<ConsumeFromWhere> chooseConsumeFromWhere() {
        ConsumeFromWhere consumeFromWhere = ConsumeFromWhere.valueOf(this.consumeFromWhere);
        return Optional.of(consumeFromWhere);
    }

    /**
     * 选择消息模式
     *
     * @return {@link DefaultMQPushConsumer#getMessageModel()}
     */
    public Optional<MessageModel> chooseMessageModel() {
        MessageModel messageModel = MessageModel.valueOf(this.messageModel);
        return Optional.of(messageModel);
    }

}
