package dev.hamal.client;

import dev.hamal.entry.input.AbstractDataInput;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @fileName: Test.java
 * @description: Test.java类说明
 * @author: by echo huang
 * @date: 2020-02-29 17:06
 */
@Component
public class Test extends AbstractDataInput<Long> {

    @Override
    public void doConsumer(Consumer<Long> dataConsumer) {

    }
}
