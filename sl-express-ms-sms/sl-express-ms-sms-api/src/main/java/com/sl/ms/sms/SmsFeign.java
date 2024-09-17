package com.sl.ms.sms;

import com.sl.ms.sms.dto.SmsInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface SmsFeign {

    /**
     * 短信发送接口，可以同时发送给多个手机号码，多个手机号用逗号隔开
     *
     * @param smsInfoDTO
     * @return
     */
    @PostMapping("send")
    ResponseEntity<Void> send(@RequestBody SmsInfoDTO smsInfoDTO);
}
