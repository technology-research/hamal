package dev.hamal.common.event;

/**
 * @author lfy
 * @date 2020-02-27 20:25
 */
public interface IReceiverInvoke {

    Object getBean();

    void invoke(DataMsg dataMsg);
}
