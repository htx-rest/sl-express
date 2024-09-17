package com.sl.ms.sms.service;

public interface ThirdChannelContainer {

    /**
     * 项目启动时将ThirdSmsService的 bean添加到service 容器中
     *
     * @param code 对应表sms_third_channel中的channel_code
     * @param thirdSmsService 第三方短信发送平台的接口实现类
     */
    void put(String code, ThirdSmsService thirdSmsService);

    /**
     * 使用第三方发送短信通道的时候，从容器中根据code获取
     *
     * @param code 对应表sms_third_channel中的channel_code
     * @return 第三方短信发送平台的接口实现类
     */
    ThirdSmsService get(String code);
}
