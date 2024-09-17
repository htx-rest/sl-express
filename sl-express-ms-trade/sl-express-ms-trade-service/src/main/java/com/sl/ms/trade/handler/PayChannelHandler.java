package com.sl.ms.trade.handler;

import com.sl.ms.trade.enums.PayChannelEnum;

/**
 * 支付渠道父接口接口，用于区别不用的支付渠道
 *
 * @author zzj
 * @version 1.0
 */
public interface PayChannelHandler {

    PayChannelEnum payChannel();
}
