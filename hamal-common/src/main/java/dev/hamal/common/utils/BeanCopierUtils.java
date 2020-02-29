package dev.hamal.common.utils;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Map;
import java.util.Objects;

import static com.google.common.hash.Hashing.crc32;

/**
 * @fileName: BeanCopierUtils.java
 * @description: bean属性拷贝工具类
 * @author: by echo huang
 * @date: 2020-02-29 15:39
 */
public class BeanCopierUtils {
    /**
     * BeanCopier的缓存
     */
    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = Maps.newConcurrentMap();

    /**
     * BeanCopier的copy
     *
     * @param source 源文件的
     * @param target 目标文件
     */
    public static void copy(Object source, Object target) {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        String key = genKey(sourceClass, targetClass);
        BeanCopier beanCopier = BEAN_COPIER_CACHE.get(key);
        if (Objects.isNull(beanCopier)) {
            beanCopier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_CACHE.putIfAbsent(key, beanCopier);
        }
        beanCopier.copy(source, target, null);
    }

    /**
     * 生成key
     *
     * @param sourceClass 源文件的class
     * @param targetClass 目标文件的class
     * @return string 生成key
     */
    private static String genKey(Class<?> sourceClass, Class<?> targetClass) {
        return crc32().newHasher().putString(sourceClass.getName() + targetClass.getName(), Charsets.UTF_8)
                .hash().toString();
    }

}
