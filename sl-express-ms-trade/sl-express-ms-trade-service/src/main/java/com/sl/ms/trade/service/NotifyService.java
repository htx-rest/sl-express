package com.sl.ms.trade.service;

import com.sl.transport.common.exception.SLException;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付通知
 *
 * @author zzj
 * @version 1.0
 */
public interface NotifyService {


    /**
     * 微信支付通知，官方文档：https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_4_5.shtml
     *
     * @param msg          通知数据
     * @param enterpriseId 商户id
     * @param signature    微信发来的签名字符串
     * @param serialNo     微信平台证书号
     * @param signStr      签名字符串
     */
    void wxPayNotify(String msg, Long enterpriseId, String signature, String serialNo, String signStr) throws SLException;

    void aliPayNotify(HttpServletRequest request, Long enterpriseId) throws SLException;
}
