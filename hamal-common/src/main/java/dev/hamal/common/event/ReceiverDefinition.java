package dev.hamal.common.event;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 数据接收者封装对象
 * @author lfy
 * @date 2020-02-27 20:27
 */
@Data
public class ReceiverDefinition implements IReceiverInvoke {
    /**
     * 事件接收者
     */
    private final Object bean;

    /**
     * 事件接收方法
     */
    private final Method method;

    /**
     * 事件类型
     */
    private final Class<? extends DataMsg> eventClz;

    public ReceiverDefinition(Object bean, Method method, Class<? extends DataMsg> eventClz) {
        this.bean = bean;
        this.method = method;
        this.eventClz = eventClz;
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public void invoke(DataMsg dataMsg) {
        //这里是事件接收者，每个接受者可能只关注那么几种数据，
        //todo 执行引过滤规则
        ReflectionUtils.makeAccessible(method);
        ReflectionUtils.invokeMethod(method, bean, dataMsg);
    }



    @SuppressWarnings("unchecked")
    public static ReceiverDefinition generateInvoker(Object bean, Method method) {
        List<ReceiverDefinition> receivers = Lists.newArrayList();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            throw new IllegalArgumentException("class " + bean.getClass().getSimpleName()
                    + "方法必须要有参数");
        }
        for (Class<?> parameterClz : parameterTypes) {
            if (DataMsg.class.isAssignableFrom(parameterClz)) {
                receivers.add(new ReceiverDefinition(bean, method, (Class<? extends DataMsg>) parameterClz));
            }
        }
        // todo 后期扩展到可接收多种事件
        if (receivers.size() > 1) {
            throw new IllegalArgumentException("class " + bean.getClass().getSimpleName()
                    + "方法只能接收一种事件");
        }
        if (receivers.isEmpty()) {
            throw new IllegalArgumentException("class " + bean.getClass().getSimpleName()
                    + "方法必须要指定接收的事件类型");
        }
        return receivers.get(0);
    }


    @Override
    public int hashCode() {
        // todo
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        // todo
        return true;
    }
}
