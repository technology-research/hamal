package com.hamal.msg;

/**
 * 事件执行结果回调
 * 用于部分业务需要事件的处理结果
 * @author lfy
 * @date 2020-02-28 20:59
 **/
public interface IEventCallback {

    /**
     * 事件执行完毕之后的回调
     * @param returnMsg 事件处理方法的返回值，每一个事件接收者处理后返回的信息都可能不一样
     */
    void callback(Object returnMsg);


    /**
     * 事件处理出现异常的处理接口
     * @param throwable 抛出的异常
     */
    void exception(Throwable throwable);
}
