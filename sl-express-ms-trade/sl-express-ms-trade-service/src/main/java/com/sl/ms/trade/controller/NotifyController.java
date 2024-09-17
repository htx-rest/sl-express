package com.sl.ms.trade.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alipay.easysdk.factory.Factory;
import com.sl.ms.trade.handler.wechat.WechatPayHttpClient;
import com.sl.ms.trade.service.NotifyService;
import com.sl.transport.common.exception.SLException;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 支付结果的通知
 *
 * @author zzj
 * @version 1.0
 */
@RestController
@Api(tags = "支付通知")
@RequestMapping("notify")
public class NotifyController {

    @Resource
    private NotifyService notifyService;

    /**
     * 微信支付成功回调
     *
     * @param msg          通知的主体内容
     * @param signature    微信发来的签名字符串
     * @param nonce        随机串
     * @param serialNo     证书序列号（微信平台）
     * @param timestamp    时间戳
     * @param enterpriseId 商户id
     * @return 正常响应200，否则响应500
     */
    @PostMapping("wx/{enterpriseId}")
    public ResponseEntity<Object> wxPayNotify(@RequestBody String msg,
                                              @RequestHeader("Wechatpay-Signature") String signature,
                                              @RequestHeader("Wechatpay-Nonce") String nonce,
                                              @RequestHeader("Wechatpay-Serial") String serialNo,
                                              @RequestHeader("Wechatpay-Timestamp") String timestamp,
                                              @PathVariable("enterpriseId") Long enterpriseId) {
        try {
            //  构建签名字符串
            // 应答时间戳\n
            // 应答随机串\n
            // 应答报文主体\n
            String signStr = StrUtil.format("{}\n{}\n{}\n", timestamp, nonce, msg);

            //微信通知的业务处理
            this.notifyService.wxPayNotify(msg, enterpriseId, signature, serialNo, signStr);

        } catch (SLException e) {
            Map<String, Object> result = MapUtil.<String, Object>builder()
                    .put("code", "FAIL")
                    .put("message", e.getMsg())
                    .build();
            //响应500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 支付宝支付成功回调 https://opendocs.alipay.com/open/194/103296?ref=api
     *
     * @param enterpriseId 商户id
     * @return 正常响应200，否则响应500
     */
    @PostMapping("alipay/{enterpriseId}")
    public ResponseEntity<Void> aliPayNotify(HttpServletRequest request,
                                             @PathVariable("enterpriseId") Long enterpriseId) {
        try {
            //支付宝通知的业务处理
            this.notifyService.aliPayNotify(request, enterpriseId);
        } catch (SLException e) {
            //响应500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(null);
    }

    public static void main(String[] args) {
        System.out.println(StrUtil.join(",", new String[]{"1"}));
    }
}
