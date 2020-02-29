package dev.hamal.entry.input;

import java.util.function.Consumer;

/**
 * @fileName: DataInput.java
 * @description: 数据入口
 * @author: by echo huang
 * @date: 2020-02-29 16:45
 */
public interface DataInput<T> {
    /**
     * 获取数据
     *
     * @param dataConsumer 数据消费者
     * @see DefaultDataInput,KafkaDataInput,AbstractDataInput<T>
     */
    void consumer(Consumer<T> dataConsumer);
}
