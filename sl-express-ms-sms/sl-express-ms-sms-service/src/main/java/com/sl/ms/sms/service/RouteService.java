package com.sl.ms.sms.service;

import com.sl.ms.sms.entity.SmsThirdChannelEntity;

public interface RouteService {

    SmsThirdChannelEntity route(Integer bussinessType, Integer smsType, Integer contentType);
}
