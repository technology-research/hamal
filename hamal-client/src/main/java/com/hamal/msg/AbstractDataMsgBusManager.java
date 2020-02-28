package com.hamal.msg;

import com.hamal.utils.ProxyBeanFactory;
import com.hamal.annotation.ReceiverMethod;

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
     * fixme 是否需要考虑并发问题
     */
    private Map<Class<? extends DataMsg>, List<IReceiverInvoke>> receiverDefinitionMap = Maps.newHashMap();


    /**
     * 注册事件接收者
     *
     * @param bean 接收者
     */

    @Override
    public void registerReceiver(Object bean) {
        Class<?> aClass = bean.getClass();
        ReflectionUtils.doWithMethods(aClass, method -> {
            if (method.isAnnotationPresent(ReceiverMethod.class)) {
                ReceiverDefinition receiverDefinition = ReceiverDefinition.generateInvoker(bean, method);
                try {
                    doRegister(receiverDefinition.getEventClz(), ProxyBeanFactory.createEnhanceReceiverInvoker(receiverDefinition));
                    log.info("订阅注册成功 : " + bean.getClass().getSimpleName());
                } catch (Exception e) {
                    log.error("订阅注册失败 : " + bean.getClass().getSimpleName(), e);
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

    @Override
    public void removeAllSubscribe(Object bean) {
        Class<?> aClass = bean.getClass();
        ReflectionUtils.doWithMethods(aClass,method -> {
            if (method.isAnnotationPresent(ReceiverMethod.class)) {
                ReceiverDefinition receiverDefinition = ReceiverDefinition.generateInvoker(bean, method);
                try {
                    doUnSubscribe(receiverDefinition.getEventClz(), ProxyBeanFactory.createEnhanceReceiverInvoker(receiverDefinition));
                    log.info("订阅取消成功 : " + bean.getClass().getSimpleName());
                } catch (Exception e) {
                    log.error("订阅取消失败 : " + bean.getClass().getSimpleName(), e);
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void removeSubscribe(Object bean, Class<? extends DataMsg> dataMsg) {
        try {
            //doUnSubscribe(dataMsg, ProxyBeanFactory.createEnhanceReceiverInvoker(ReceiverDefinition.generateInvoker(bean, method)));
            log.info("订阅取消成功 : " + bean.getClass().getSimpleName());
        } catch (Exception e) {
            log.error("订阅取消失败 : " + bean.getClass().getSimpleName(), e);
            throw new RuntimeException(e);
        }
    }

    private void doUnSubscribe(Class<? extends DataMsg> eventClz, IReceiverInvoke enhanceReceiverInvoker){

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
