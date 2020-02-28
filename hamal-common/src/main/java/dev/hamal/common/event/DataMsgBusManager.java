package dev.hamal.common.event;

import dev.hamal.common.event.DataMsg;
import dev.hamal.common.event.IEventCallback;

/**
 * @author lfy
 * @date 2020-02-27 20:48
 */
public interface DataMsgBusManager {

    /**
     * 异步提交事件
     *
     * @param event          事件
     * @param eventName      事件名
     * @param dispatcherCode 分发code
     */
    void asynSubmit(DataMsg event, String eventName, int dispatcherCode);

    /**
     * 异步提交事件
     *
     * @param event          事件
     * @param eventName      事件名
     * @param dispatcherCode 分发code
     * @param callback       事件执行完毕的回调
     */
    void asynSubmit(DataMsg event, String eventName, int dispatcherCode, IEventCallback callback);

    /**
     * 异步提交事件
     *
     * @param event          事件
     * @param eventName      事件名
     * @param dispatcherCode 分发code
     * @param callback       事件执行完毕的回调
     * @param contexts       事件上下文
     */
    void asynSubmitWithContext(DataMsg event, String eventName, int dispatcherCode, IEventCallback callback, Object... contexts);

    /**
     * 异步提交事件
     *
     * @param event          事件
     * @param eventName      事件名
     * @param dispatcherCode 分发code
     * @param contexts       事件上下文
     */
    void asynSubmitWithContext(DataMsg event, String eventName, int dispatcherCode, Object... contexts);

    /**
     * 同步提交事件
     *
     * @param event 事件
     */
    void synSubmit(DataMsg event);

    /**
     * 同步提交事件
     *
     * @param event    事件
     * @param callback 事件执行完毕的回调
     */
    void synSubmit(DataMsg event, IEventCallback callback);

    /**
     * 同步提交事件
     *
     * @param event    事件
     * @param contexts 事件执行上下文
     */
    void synSubmitWithContext(DataMsg event, Object... contexts);

    /**
     * 同步提交事件
     *
     * @param event    事件
     * @param callback 事件执行完毕的回调
     * @param contexts 事件执行上下文
     */
    void synSubmitWithContext(DataMsg event, IEventCallback callback, Object... contexts);

    /**
     * 事件处理异常抛出
     *
     * @param event 事件
     */
    void synSubmitThrowException(Object event);

    /**
     * 订阅
     *
     * @param bean 订阅者
     */
    void registerReceiver(Object bean);

    /**
     * 删除所有订阅
     *
     * @param bean 订阅者
     */
    void removeAllSubscribe(Object bean);

    /**
     * 移除指定的订阅
     *
     * @param bean  订阅者
     * @param event 订阅
     */
    void removeSubscribe(Object bean, Class<? extends DataMsg> event);

}
