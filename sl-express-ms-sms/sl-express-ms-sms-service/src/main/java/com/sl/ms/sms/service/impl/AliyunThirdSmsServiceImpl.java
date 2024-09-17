package com.sl.ms.sms.service.impl;

import com.sl.ms.sms.dto.PlatformSmsInfoDTO;
import com.sl.ms.sms.service.ThirdSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("aliyun")
@Slf4j
public class AliyunThirdSmsServiceImpl implements ThirdSmsService {
    @Override
    public void send(PlatformSmsInfoDTO platformSmsInfoDTO) {
        //第三方发送短信验证码
        log.info("短信发送成功 ...");
        log.info("platformSmsInfoDTO：{}", platformSmsInfoDTO);

    }
}
