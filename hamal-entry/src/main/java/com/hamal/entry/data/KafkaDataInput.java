package com.hamal.entry.data;

import java.util.function.Consumer;

/**
 * @fileName: KafkaDataInput.java
 * @description: KafkaDataInput.java类说明
 * @author: by echo huang
 * @date: 2020-02-28 20:25
 */
public class KafkaDataInput implements DataInput<String>{

    @Override
    public void consume(Consumer<String> dataConsumer) {
    }
}
