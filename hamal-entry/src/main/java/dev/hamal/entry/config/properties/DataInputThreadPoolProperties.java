package dev.hamal.entry.config.properties;

import lombok.Data;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @fileName: DataInputThreadPoolProperties.java
 * @description: 数据入口线程池参数配置
 * @author: by echo huang
 * @date: 2020-02-29 17:32
 */
@Data
public class DataInputThreadPoolProperties {

    /**
     * 核心线程数 默认为:cpu核数一半
     */
    private Integer corePoolSize = Runtime.getRuntime().availableProcessors() / 2;
    /**
     * 最大线程数 默认为:cpu核数
     */
    private Integer maximumPoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * 空闲线程存活时间 默认:为10
     */
    private Long keepAliveTime = 10L;

    /**
     * 空闲线程存活时间单位 默认为:分钟
     */
    private String unit = TimeUnit.MINUTES.name();
    /**
     * 阻塞队列大小 默认为:2000
     */
    private Integer workQueueSize = 2000;
    /**
     * 线程名称前缀 默认为:"Hamal-DataInput-Thread";
     */
    private String threadNamePrefix = "Hamal-DataInput-Thread";

    /**
     * 拒绝策略 默认为:CallerRunsPolicy 在当前线程执行
     * 仅支持: CallerRunsPolicy 运行在调用线程上
     * AbortPolicy      中断抛出异常
     * DiscardOldestPolicy  丢弃工作队列中最老的任务
     * DiscardPolicy    丢失任务
     */
    private String rejectedStrategy = "CallerRunsPolicy";

    /**
     * 选择对应拒绝策略
     *
     * @return {@link RejectedExecutionHandler}
     */
    public RejectedExecutionHandler chooseRejectedStrategy() {
        RejectedExecutionHandler handler;
        switch (this.rejectedStrategy) {
            case "CallerRunsPolicy":
                handler = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            case "AbortPolicy":
                handler = new ThreadPoolExecutor.AbortPolicy();
                break;
            case "DiscardOldestPolicy":
                handler = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            default:
                handler = new ThreadPoolExecutor.DiscardPolicy();
        }
        return handler;
    }

    /**
     * 选择时间单位
     *
     * @return {@link TimeUnit}
     */
    public TimeUnit chooseTimeUnit() {
        return TimeUnit.valueOf(this.unit);
    }
}
