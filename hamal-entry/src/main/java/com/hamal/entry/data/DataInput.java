package com.hamal.entry.data;

import java.util.function.Consumer;

/**
 * @fileName: DataInput.java
 * @description: 数据入口
 * @author: by echo huang
 * @date: 2020-02-28 19:56
 */
public interface DataInput<T> {
    /**
     * 消费消息
     *
     * @param dataConsumer 数据消费者
     * @return 解析后数据
     */
    void consume(Consumer<T> dataConsumer);
}
