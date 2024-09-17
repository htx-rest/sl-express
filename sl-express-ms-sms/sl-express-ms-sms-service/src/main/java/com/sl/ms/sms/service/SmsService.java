package com.sl.ms.sms.service;

import com.sl.ms.sms.dto.SmsInfoDTO;

public interface SmsService {

    /**
     * 单个手机号发送短信验证码接口
     *
     * @param smsInfoDTO
     */
    void singleSend(SmsInfoDTO smsInfoDTO);
}
