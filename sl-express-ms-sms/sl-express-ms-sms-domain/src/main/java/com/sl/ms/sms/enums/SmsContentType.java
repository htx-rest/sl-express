package com.sl.ms.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信内容类型枚举
 */
@Getter
@AllArgsConstructor
public enum SmsContentType {
    WORD_MESSAGE(1, "文字类型短信"), VOICE(2, "语音类型短信");
    private int type;
    private String desc;

    public static String desc(int type) {
        for (SmsContentType smsContentType : values()) {
            if (smsContentType.type == type) {
                return smsContentType.desc;
            }
        }
        return null;
    }
}
