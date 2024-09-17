package com.sl.ms.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信类型枚举
 */
@Getter
@AllArgsConstructor
public enum SmsType {
    VERIFY(1, "验证类型短信"), NOTICE(2, "通知类型短信");
    private int type;
    private String desc;

    public static String desc(int type) {
        for (SmsType smsType : values()) {
            if (type == smsType.getType()) {
                return smsType.getDesc();
            }
        }
        return null;
    }
    }
