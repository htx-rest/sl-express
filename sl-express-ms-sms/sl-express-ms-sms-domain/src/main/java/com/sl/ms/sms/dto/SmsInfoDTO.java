package com.sl.ms.sms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("12")
public class SmsInfoDTO {

    /**
     * 业务类型1：用户端，2：司机端，3：快递员端，4：后台管理系统
     */
    @ApiModelProperty(value = "业务类型1：用户端，2：司机端，3：快递员端，4：后台管理系统", required = true)
    @NotNull(message = "业务类型不能为空")
    private Integer bussinessType;

    @ApiModelProperty(value = "短信类型，1：验证类型短信，2：通知类型短信", required = true)
    @NotNull(message = "短信类型不能为空")
    private Integer smsType;

    @ApiModelProperty("内容类型，1：文字短信，2：语音短信")
    @NotNull(message = "内容类型不能为空")
    private Integer contentType;

    @ApiModelProperty(value = "手机号，多个手机号用逗号隔开", required = false)
    @NotNull(message = "手机号不能为空")
    private String mobiles;

    @ApiModelProperty("短信验证码")
    private String verifyCode;

    @ApiModelProperty("用户唯一标识")
    private Long userId;

}
