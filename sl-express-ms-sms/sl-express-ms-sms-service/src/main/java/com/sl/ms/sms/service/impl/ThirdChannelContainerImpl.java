package com.sl.ms.sms.service.impl;

import com.sl.ms.sms.service.ThirdChannelContainer;
import com.sl.ms.sms.service.ThirdSmsService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ThirdChannelContainerImpl implements ThirdChannelContainer {
    private Map<String, ThirdSmsService> smsServiceContainer = new HashMap<>();

    @Override
    public void put(String code, ThirdSmsService thirdSmsService) {
        smsServiceContainer.put(code, thirdSmsService);
    }

    @Override
    public ThirdSmsService get(String code) {
        return smsServiceContainer.get(code);
    }
}
