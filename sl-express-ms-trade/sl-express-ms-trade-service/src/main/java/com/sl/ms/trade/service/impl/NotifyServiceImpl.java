package com.sl.ms.trade.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.sl.ms.trade.constant.TradingCacheConstant;
import com.sl.ms.trade.constant.TradingConstant;
import com.sl.ms.trade.entity.TradingEntity;
import com.sl.ms.trade.enums.TradingEnum;
import com.sl.ms.trade.enums.TradingStateEnum;
import com.sl.ms.trade.handler.alipay.AlipayConfig;
import com.sl.ms.trade.handler.wechat.WechatPayHttpClient;
import com.sl.ms.trade.service.NotifyService;
import com.sl.ms.trade.service.TradingService;
import com.sl.ms.trade.util.WeiXinAesUtil;
import com.sl.transport.common.exception.SLException;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 支付成功的通知处理
 *
 * @author zzj
 * @version 1.0
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    @Resource
    private TradingService tradingService;
    @Resource
    private RedissonClient redissonClient;

    @Override
    public void wxPayNotify(String msg, Long enterpriseId, String signature, String serialNo, String signStr) throws SLException {
        // 解析数据
        JSONObject jsonObject = JSONUtil.parseObj(msg);
        if (!StrUtil.equals("TRANSACTION.SUCCESS", jsonObject.getStr("event_type"))) {
            //非成功请求直接返回，理论上都是成功的请求
            return;
        }
        // 查询配置
        WechatPayHttpClient client = WechatPayHttpClient.get(enterpriseId);


        //验证签名，确保请求来自微信
        try {
            //确保在管理器中存在自动更新的商户证书
            client.createHttpClient();

            CertificatesManager certificatesManager = CertificatesManager.getInstance();
            Verifier verifier = certificatesManager.getVerifier(client.getMchId());
            boolean verify = verifier.verify(serialNo, signStr.getBytes(StandardCharsets.UTF_8), signature);
            if (!verify) {
                throw new SLException("验签失败");
            }
        } catch (Exception e) {
            throw new SLException("没有查询到证书");
        }

        //实例化aes工具类
        WeiXinAesUtil weiXinAesUtil = new WeiXinAesUtil(client.getApiV3Key().getBytes(StandardCharsets.UTF_8));

        //附加数据
        byte[] associatedData = jsonObject.getByPath("resource.associated_data", String.class).getBytes(StandardCharsets.UTF_8);
        //随机串
        byte[] nonce = jsonObject.getByPath("resource.nonce", String.class).getBytes(StandardCharsets.UTF_8);
        //数据密文
        String ciphertext = jsonObject.getByPath("resource.ciphertext", String.class);

        JSONObject jsonData;
        try {
            //解密
            String data = weiXinAesUtil.decryptToString(associatedData, nonce, ciphertext);
            jsonData = JSONUtil.parseObj(data);
        } catch (Exception e) {
            throw new SLException("解密失败");
        }

        if (!StrUtil.equals(jsonData.getStr("trade_state"), TradingConstant.WECHAT_TRADE_SUCCESS)) {
            return;
        }

        //交易单号
        Long tradingOrderNo = jsonData.getLong("out_trade_no");
        log.info("微信支付通知：tradingOrderNo = {}, data = {}", tradingOrderNo, jsonData);

        //更新交易单
        this.updateTrading(tradingOrderNo, jsonData.getStr("trade_state_desc"), jsonData.toString());
    }

    private void updateTrading(Long tradingOrderNo, String resultMsg, String resultJson) {
        String key = TradingCacheConstant.CREATE_PAY + tradingOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            //获取锁
            if (lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS)) {
                TradingEntity trading = this.tradingService.findTradByTradingOrderNo(tradingOrderNo);
                if (trading.getTradingState() == TradingStateEnum.YJS) {
                    // 已付款
                    return;
                }

                //设置成付款成功
                trading.setTradingState(TradingStateEnum.YJS);
                //清空二维码数据
                trading.setQrCode("");
                trading.setResultMsg(resultMsg);
                trading.setResultJson(resultJson);
                this.tradingService.saveOrUpdate(trading);
                return;
            }
        } catch (Exception e) {
            throw new SLException("处理业务失败");
        } finally {
            lock.unlock();
        }
        throw new SLException("处理业务失败");
    }

    @Override
    public void aliPayNotify(HttpServletRequest request, Long enterpriseId) throws SLException {
        //获取参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> param = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            param.put(entry.getKey(), StrUtil.join(",", entry.getValue()));
        }

        String tradeStatus = param.get("trade_status");
        if (!StrUtil.equals(tradeStatus, TradingConstant.ALI_TRADE_SUCCESS)) {
            return;
        }

        //查询配置
        Config config = AlipayConfig.getConfig(enterpriseId);
        Factory.setOptions(config);
        try {
            Boolean result = Factory
                    .Payment
                    .Common().verifyNotify(param);
            if (!result) {
                throw new SLException("验签失败");
            }
        } catch (Exception e) {
            throw new SLException("验签失败");
        }

        //获取交易单号
        Long tradingOrderNo = Convert.toLong(param.get("out_trade_no"));
        //更新交易单
        this.updateTrading(tradingOrderNo, "支付成功", JSONUtil.toJsonStr(param));
    }
}
