package com.sl.ms.sms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sl.ms.sms.entity.SmsThirdChannelEntity;
import com.sl.ms.sms.mapper.SmsThirdChannelMapper;
import com.sl.ms.sms.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private SmsThirdChannelMapper smsThirdChannelMapper;

    /**
     * 目前只根据优先级进行路由选出前五，然后随机选择渠道
     *
     * @param bussinessType
     * @param smsType
     * @param contentType
     * @return
     */
    @Override
    public SmsThirdChannelEntity route(Integer bussinessType, Integer smsType, Integer contentType) {

        //取前五条记录
        QueryWrapper<SmsThirdChannelEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bussiness_type", bussinessType)
                .eq("sms_type", smsType)
                .eq("content_type", contentType);
        queryWrapper.orderBy(true, false, "sms_priority");
        queryWrapper.last("limit 5");
        List<SmsThirdChannelEntity> smsThirdChannelEntities = smsThirdChannelMapper.selectList(queryWrapper);

        if (CollectionUtil.isEmpty(smsThirdChannelEntities)) {
            return null;
        }

        //随机选择
        int randomNumber = (int) (smsThirdChannelEntities.size() * Math.random());
        return smsThirdChannelEntities.get(randomNumber);
    }
}
