package com.cheerfun.hamal.utils;

import com.cheerfun.hamal.annotation.ReceiverMethod;
import com.cheerfun.hamal.msg.DataMsg;
import com.cheerfun.hamal.msg.IReceiverInvoke;
import com.cheerfun.hamal.msg.ReceiverDefinition;
import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 代理类生成工具
 *
 * @author lfy
 * @date 2020-02-27 20:37
 */
public class ProxyBeanFactory {

    private static final ClassPool classPool = ClassPool.getDefault();

    private static final AtomicInteger index = new AtomicInteger(0);

    @SuppressWarnings("unchecked")
    public static IReceiverInvoke createEnhanceReceiverInvoker(ReceiverDefinition definition) throws Exception {
        Object bean = definition.getBean();
        Method method = definition.getMethod();
        String methodName = method.getName();
        Class<?> beanClazz = bean.getClass();

        CtClass enhanceClazz = buildCtClass(IReceiverInvoke.class);

        CtField ctField = new CtField(classPool.get(beanClazz.getCanonicalName()), "bean", enhanceClazz);
        ctField.setModifiers(Modifier.PRIVATE);
        enhanceClazz.addField(ctField);

        String[] parameters = new String[]{beanClazz.getCanonicalName()};
        CtConstructor ctConstructor = new CtConstructor(classPool.get(parameters), enhanceClazz);
        ctConstructor.setBody("{this.bean = $1;}");
        ctConstructor.setModifiers(Modifier.PUBLIC);
        enhanceClazz.addConstructor(ctConstructor);

        // 构建 invoke 方法
        CtMethod ctMethod = new CtMethod(classPool.get(void.class.getCanonicalName())
                , "invoke", classPool.get(new String[]{DataMsg.class.getCanonicalName()})
                , enhanceClazz);
        ctMethod.setModifiers(Modifier.PUBLIC);
        StringBuilder methodBuilder = new StringBuilder();
        methodBuilder.append("{");
        methodBuilder.append("bean." + methodName + "((" + definition.getEventClz().getCanonicalName() + ") $1);");
        methodBuilder.append("}");
        System.out.println(methodBuilder.toString());
        ctMethod.setBody(methodBuilder.toString());
        enhanceClazz.addMethod(ctMethod);

        // 构建 equals() 方法
        CtMethod ctEqualsMethod = new CtMethod(classPool.get(boolean.class.getCanonicalName())
                , "equals", classPool.get(new String[]{Object.class.getCanonicalName()})
                , enhanceClazz);
        ctEqualsMethod.setModifiers(Modifier.PUBLIC);
        methodBuilder = new StringBuilder();
        methodBuilder.append("{");
        methodBuilder.append("com.cheerfun.hamal.msg.IReceiverInvoke other = (com.cheerfun.hamal.msg.IReceiverInvoke)$1;");
        methodBuilder.append("return bean.equals(other.getBean());");
        methodBuilder.append("}");
        ctEqualsMethod.setBody(methodBuilder.toString());
        enhanceClazz.addMethod(ctEqualsMethod);

        // 构建 hashcode() 方法
        CtMethod ctHashCodeMethod = new CtMethod(classPool.get(int.class.getCanonicalName())
                , "hashCode", classPool.get(new String[]{})
                , enhanceClazz);
        ctHashCodeMethod.setModifiers(Modifier.PUBLIC);
        methodBuilder = new StringBuilder();
        methodBuilder.append("{");
        methodBuilder.append("return bean.hashCode();");
        methodBuilder.append("}");
        ctHashCodeMethod.setBody(methodBuilder.toString());
        enhanceClazz.addMethod(ctHashCodeMethod);

        // 构建 getBean() 方法
        CtMethod ctGetBeanMethod = new CtMethod(classPool.get(Object.class.getCanonicalName())
                , "getBean", classPool.get(new String[]{})
                , enhanceClazz);
        ctGetBeanMethod.setModifiers(Modifier.PUBLIC);
        methodBuilder = new StringBuilder();
        methodBuilder.append("{");
        methodBuilder.append("return this.bean;");
        methodBuilder.append("}");
        ctGetBeanMethod.setBody(methodBuilder.toString());
        enhanceClazz.addMethod(ctGetBeanMethod);

        //  ctClass ---> Class
        Class rClz = enhanceClazz.toClass();

        // debug一下
        //enhanceClazz.writeFile("/Volumes/file/project/hamal/hamal-client/src/java/com/cheerfun/hamal/utils/");

        Constructor<?> constructor = rClz.getConstructor(beanClazz);
        IReceiverInvoke result = (IReceiverInvoke) constructor.newInstance(bean);
        return result;
    }

    private static CtClass buildCtClass(Class<?> clazz) throws Exception {
        CtClass ctClass = classPool.makeClass(clazz.getSimpleName() + "Enhance" + index.incrementAndGet());
        ctClass.addInterface(classPool.get(clazz.getCanonicalName()));
        return ctClass;
    }


    /**
     * ---------------------simple test--------------------
     */
    public static void main(String[] args) throws Exception {
        Demo demo = new Demo();
        Class<? extends Demo> aClass = demo.getClass();
        IReceiverInvoke enhanceReceiverInvoker = createEnhanceReceiverInvoker(ReceiverDefinition.generateInvoker(demo, aClass.getMethods()[0]));
        enhanceReceiverInvoker.invoke(new DemoMsg());
    }
}
