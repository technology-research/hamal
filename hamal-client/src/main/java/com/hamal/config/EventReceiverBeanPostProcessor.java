package com.hamal.config;

import com.hamal.msg.DataMsgBusManager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

/**
 * @author lfy
 * @date 2020-02-27 20:24
 */
public class EventReceiverBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements ApplicationContextAware, Ordered {

	private ApplicationContext applicationContext;

	@Override
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		DataMsgBusManager eventBusManager = applicationContext.getBean(DataMsgBusManager.class);
		try {
			eventBusManager.registerReceiver(bean);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return super.postProcessAfterInstantiation(bean,beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}
