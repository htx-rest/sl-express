package com.sl.ms.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sl.transport.common.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("sms_third_channel")
public class SmsThirdChannelEntity extends BaseEntity {

    private Integer bussinessType;

    private Integer smsType;

    private Integer contentType;

    private String smsTemplate;

    private String thirdTemplateCode;

    private String channelCode;

    private Integer smsPriority;

    private String signName;

    private Integer status;

    private Long creater;

    private Long updater;

    private Boolean isDelete;

}