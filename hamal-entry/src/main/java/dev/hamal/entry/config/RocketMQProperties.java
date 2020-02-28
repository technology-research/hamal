package dev.hamal.entry.config;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @fileName: RocketMQProperties.java
 * @description: rocketMQ参数配置
 * @author: by echo huang
 * @date: 2020-02-28 21:41
 */
@Data
public class RocketMQProperties {
    /**
     * nameSrv地址
     */
    private String nameSrv = "localhost";
    ;
    /**
     * 订阅topic
     */
    private List<String> topic = Lists.newArrayList("Hamal_Binlog");
    ;
    /**
     * 消费者组
     */
    private String consumeGroup = "Hamal_Consumer_Group";
    ;
    /**
     * 消费最小线程数
     */
    private int consumeThreadMin = 30;
    ;
    /**
     * 消费最大线程数
     */
    private int consumeThreadMax = 30;
    ;
    /**
     * 消费模型 默认为集群
     */
    private MessageModel messageModel = MessageModel.CLUSTERING;
    ;
    /**
     * 本地缓存最大消息数量超过后本地消费流控
     */
    private int pullThresholdForQueue = 2000;
    ;
    /**
     * 消息队列最大缓存的大小单位MB
     */
    private int pullThresholdSizeForQueue = 200;
    /**
     * 每次最多拉取的消息个数
     */
    private int pullBatchSize = 32;

}
