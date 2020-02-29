package dev.hamal.entry;

import dev.hamal.entry.input.AbstractDataInput;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @fileName: Test.java
 * @description: Test.java类说明
 * @author: by echo huang
 * @date: 2020-02-29 16:35
 */
public class Test {
    @Autowired
    private AbstractDataInput<String> abstractDataInput;

    public void consumer(){
        abstractDataInput.consumer(data->{
            //todo data是你们要的数据
        });
    }
}
