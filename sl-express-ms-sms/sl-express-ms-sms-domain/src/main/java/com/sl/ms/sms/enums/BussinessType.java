package com.sl.ms.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 使用短信通道的类型枚举
 */
@Getter
@AllArgsConstructor
public enum BussinessType {

    CLIENT(1, "c端用户"), DRIVER(2, "司机端用户"), COURIER(3, "快递员端用户"), MANAGER(4, "管理员端用户");
    private int type;
    private String desc;

    public static String desc(int type) {
        for (BussinessType bussinessType : values()) {
            if (type == bussinessType.getType()) {
                return bussinessType.desc;
            }
        }
        return null;
    }
}
