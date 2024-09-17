package com.sl.ms.sms.controller;

import com.sl.ms.sms.dto.SmsInfoDTO;
import com.sl.ms.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "短信验证码发送相关接口")
@RestController
@RequestMapping("/sms")
@Slf4j
public class SmsController {

    @Autowired
    private SmsService smsService;

    @ApiOperation("发送单个短信")
    @PostMapping("send")
    public ResponseEntity<Void> send(@RequestBody SmsInfoDTO smsInfoDTO) {
        smsService.singleSend(smsInfoDTO);
        return ResponseEntity.ok(null);
    }
}
