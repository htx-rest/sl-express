package com.htx.common.enums;

/**
 * 所有的错异常类型枚举都需要实现该接口
 */
public interface BaseExceptionEnum extends BaseEnum {

    /**
     * http响应状态码
     */
    Integer getStatus();

}
