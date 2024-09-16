package com.htx.common.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class ObjectUtils {

    /**
     * 将原始对象origin转换成指定类型的对象
     *
     * @param origin          原始对象
     * @param clazz           转换成的对象的class
     * @param objectConverter 用来转换特定的字段
     * @param <X>             转化成的对象的类型
     * @return
     */
    public static <X, Y> X convert(Y origin, Class<X> clazz, ObjectConverter<X,Y> objectConverter) {
        if (origin == null) {
            return null;
        }
        JSONObject entries = JSONUtil.parseObj(origin);
        if (entries == null) {
            return null;
        }
        X result = JSONUtil.toBean(entries, clazz);
        if (objectConverter != null) {
            objectConverter.convert(origin, result);
        }
        return result;
    }

    public static <T> T convert(Object origin, Class<T> clazz) {
        return convert(origin, clazz, null);
    }

    public interface ObjectConverter<X,Y> {
        void convert(Y y,X x);
    }

    /**
     * 为object设置默认值，对target中的基本类型进行默认值初始化,
     * 为null的对象不操作
     *
     * @param target 目标对象
     */
    public static void setDefault(Object target) {
        if (target == null) {
            return;
        }
        Class<?> clazz = target.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            setDefault(field, target);
        }

    }

    private static void setDefault(Field field, Object target) {
        field.setAccessible(true);
        try {
            Object value = field.get(target);
            if (value != null) {
                return;
            }
            String type = field.getGenericType().toString();
            Object defaultValue = null;
            switch (type) {
                case "class java.lang.String":
                case "class java.lang.Character":
                    defaultValue = "";
                    break;
                case "class java.lang.Double":
                    defaultValue = 0.0d;
                    break;
                case "class java.lang.Long":
                    defaultValue = 0L;
                    break;
                case "class java.lang.Short":
                    defaultValue = (short) 0;
                    break;
                case "class java.lang.Integer":
                    defaultValue = 0;
                    break;
                case "class java.lang.Float":
                    defaultValue = 0f;
                    break;
                case "class java.lang.Byte":
                    defaultValue = (byte) 0;
                    break;
                case "class java.math.BigDecimal":
                    defaultValue = BigDecimal.ZERO;
                    break;
                case "class java.lang.Boolean":
                    defaultValue = Boolean.FALSE;
                    break;
                default:
                    defaultValue = null;

            }
            field.set(target, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
