package com.sl.ms.trade.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sl.ms.trade.enums.PayChannelEnum;

import java.util.Map;

/**
 * @author zzj
 * @version 1.0
 */
public class HandlerFactory {

    private HandlerFactory() {

    }

    public static <T> T get(PayChannelEnum payChannel, Class<T> handler) {
        Map<String, T> beans = SpringUtil.getBeansOfType(handler);
        for (Map.Entry<String, T> entry : beans.entrySet()) {
            Object obj = ReflectUtil.invoke(entry.getValue(), "payChannel");
            if (ObjectUtil.equal(payChannel, obj)) {
                return (T) entry.getValue();
            }
        }
        return null;
    }

    public static <T> T get(String payChannel, Class<T> handler) {
        return get(PayChannelEnum.valueOf(payChannel), handler);
    }
}
