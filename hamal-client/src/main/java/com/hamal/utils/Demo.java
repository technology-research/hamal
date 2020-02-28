package com.hamal.utils;

import com.hamal.annotation.ReceiverMethod;

/**
 * @author lfy
 * @date 2020-02-28 01:28
 */
public class Demo {

    @ReceiverMethod(schemaName = "LuoBoShiDaiBi",tableName = "ZeLinYeShiDaiBi")
    public void say(DemoMsg dataMsg) {
        System.out.println(11111);
    }
}
