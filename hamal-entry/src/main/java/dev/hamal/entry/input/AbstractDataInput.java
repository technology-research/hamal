package dev.hamal.entry.input;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * @fileName: AbstractDataInput.java
 * @description: 数据入口模版
 * @author: by echo huang
 * @date: 2020-02-28 19:51
 */
public abstract class AbstractDataInput<T> implements DataInput<T> {

    @Resource(name = "dataExecutor")
    private ExecutorService executorService;

    /**
     * 对外暴漏入口
     *
     * @param dataConsumer 数据消费者
     */
    @Override
    public void consumer(Consumer<T> dataConsumer) {
        executorService.execute(() -> doConsumer(dataConsumer));
    }

    /**
     * 消费逻辑
     *
     * @param dataConsumer 外部传入数据消费者
     * @implNote 目前内置支持RocketMQ和Kafka方式，支持用户自定义扩展
     * @apiNote 默认rocketmq、kafka消费数据为String类型
     */
    public abstract void doConsumer(Consumer<T> dataConsumer);
}
