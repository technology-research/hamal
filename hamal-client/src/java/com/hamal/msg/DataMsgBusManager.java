package com.hamal.msg;

/**
 * @author lfy
 * @date 2020-02-27 20:48
 */
public interface DataMsgBusManager {

    void submit(final DataMsg msg, final int dispatcherCode);

    void synSubmit(final DataMsg dataMsg);

    void registerReceiver(Object bean);

    void removeAllSubscribe(Object bean);

    void removeSubscribe(Object bean,  Class<? extends DataMsg> dataMsg);

}
