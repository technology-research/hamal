package com.hamal.entry.data;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.function.Consumer;

/**
 * @fileName: RocketMQDataInput.java
 * @description: RocketMQDataInput.java类说明
 * @author: by echo huang
 * @date: 2020-02-28 20:25
 */
public class RocketMQDataInput implements DataInput<String> {

    @Override
    public void consume(Consumer<String> dataConsumer) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("namespace", "group");
//        consumer.setConsumeThreadMax();
//        consumer.setConsumeThreadMin();
//        consumer.setConsumerGroup();
        consumer.setInstanceName("hello");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                dataConsumer.accept("hjello");
                return null;
            }
        });
    }

}
