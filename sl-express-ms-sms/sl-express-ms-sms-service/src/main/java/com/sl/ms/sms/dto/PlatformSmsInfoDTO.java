package com.sl.ms.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 发送第三方短信内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformSmsInfoDTO {
    //发送短信唯一标识
    private Long id;
    //发送手机号
    private String mobiles;
    //短信内容
    private String content;
    //短信验证码
    private String verifyCode;
    //签名
    private String signName;
    //第三方模板编码
    private String thirdTemplateCode;
    //发送者唯一标识
    private Long userId;
}
