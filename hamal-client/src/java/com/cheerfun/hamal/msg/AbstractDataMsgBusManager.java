package com.cheerfun.hamal.msg;

import com.cheerfun.hamal.annotation.ReceiverMethod;
import com.cheerfun.hamal.utils.ProxyBeanFactory;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lfy
 * @date 2020-02-27 20:46
 */
@Slf4j
@Component
public abstract class AbstractDataMsgBusManager implements DataMsgBusManager {


    /**
     * 事件 -->事件接收者
     */
    private Map<Class<? extends DataMsg>, List<IReceiverInvoke>> receiverDefinitionMap = Maps.newHashMap();


    /**
     * 注册事件接收者
     *
     * @param bean
     */
    @Override
    public void registerReceiver(Object bean) {
        Class<?> aClass = bean.getClass();
        ReflectionUtils.doWithMethods(aClass, method -> {
            if (method.isAnnotationPresent(ReceiverMethod.class)) {
                ReceiverDefinition receiverDefinition = ReceiverDefinition.generateInvoker(bean, method);
                try {
                    doRegister(receiverDefinition.getEventClz(), ProxyBeanFactory.createEnhanceReceiverInvoker(receiverDefinition));
                    log.info("事件注册成功 : " + bean.getClass().getSimpleName());
                } catch (Exception e) {
                    log.error("事件注册失败 : " + bean.getClass().getSimpleName(), e);
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void doRegister(Class<? extends DataMsg> eventClz, IReceiverInvoke enhanceReceiverInvoker) {
        if (!receiverDefinitionMap.containsKey(eventClz)) {
            receiverDefinitionMap.put(eventClz, new CopyOnWriteArrayList<>());
        }
        List<IReceiverInvoke> receiverInvokes = receiverDefinitionMap.get(eventClz);
        if (!receiverInvokes.contains(enhanceReceiverInvoker)) {
            receiverInvokes.add(enhanceReceiverInvoker);
        }
    }

    public void removeAllSubscribe(Object bean) {
        //todo
    }

    public void removeSubscribe(Object bean, DataMsg dataMsg) {
        //todo
    }


    @Override
    public int hashCode() {
        // TODO
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        // TODO
        return false;
    }
}
