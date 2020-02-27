package com.cheerfun.hamal.annotation;

import java.lang.annotation.*;

/**
 * @author lfy
 * @date 2020-02-27 20:10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {
        ElementType.TYPE,
        ElementType.METHOD
})
public @interface ReceiverMethod {

    /**
     * 指定订阅的数据库，默认是订阅所有库的数据
     */
    String schemaName() default "";

    /**
     * 指定订阅的数据表，有三种情况
     * 1 若dbName为空，则订阅所有库的数据
     * 2 若dbName不为空，table为空，则订阅dbName指定库的所有数据
     * 3 若dbName不为空，table不为空，则订阅指定dbName库的指定表数据
     */
    String tableName() default "";
}
