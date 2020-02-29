package dev.hamal.entry.input;

import dev.hamal.common.exception.HamalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @fileName: DefaultDataInput.java
 * @description: DefaultDataInput.java类说明
 * @author: by echo huang
 * @date: 2020-02-28 20:25
 */
@Slf4j
public class DefaultDataInput extends AbstractDataInput<String> {


    private DefaultMQPushConsumer consumer;
    private String topic;


    public DefaultDataInput(DefaultMQPushConsumer consumer, String topic) {
        this.consumer = consumer;
        this.topic = topic;
    }

    @PostConstruct
    public void init() {
        try {
            consumer.subscribe(this.topic, "*");
        } catch (MQClientException e) {
            throw new HamalException(String.format("【RocketMQ】topic:%s subscribe fail", this.topic));
        }
    }


    @Override
    public void doConsumer(Consumer<String> dataConsumer) {
        this.consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                try {
                    //防止空消费
                    if (CollectionUtils.isEmpty(messages)) {
                        log.error("【RocketMQ】topic:{} message is null", topic);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    long offset = messages.get(0).getQueueOffset();
                    String maxOffset =
                            messages.get(0).getProperty(MessageConst.PROPERTY_MAX_OFFSET);
                    long diff = Long.parseLong(maxOffset) - offset;
                    if (diff > 10000) {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    for (MessageExt msg : messages) {
                        //字节数据转换为字符
                        String messageBody = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        //传递数据给下游
                        dataConsumer.accept(messageBody);
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (UnsupportedEncodingException e) {
                    log.error("【RocketMQ】topic:{} message parse fail", topic);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

            }
        });
    }
}
