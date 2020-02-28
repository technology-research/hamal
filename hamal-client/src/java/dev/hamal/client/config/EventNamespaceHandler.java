package dev.hamal.client.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author lfy
 * @date 2020-02-27 20:20
 */
public class EventNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("config",new EventBeanDefinitionParser());
	}
}
