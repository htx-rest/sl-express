package com.sl.ms.sms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sl.ms.sms.dto.PlatformSmsInfoDTO;
import com.sl.ms.sms.dto.SmsInfoDTO;
import com.sl.ms.sms.entity.SmsRecordEntity;
import com.sl.ms.sms.entity.SmsThirdChannelEntity;
import com.sl.ms.sms.mapper.SmsRecordMapper;
import com.sl.ms.sms.mapper.SmsThirdChannelMapper;
import com.sl.ms.sms.service.RouteService;
import com.sl.ms.sms.service.SmsService;
import com.sl.ms.sms.service.ThirdChannelContainer;
import com.sl.ms.sms.service.ThirdSmsService;
import com.sl.transport.common.exception.SLException;
import com.sl.transport.common.util.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RouteService routeService;

    @Autowired
    private SmsRecordMapper smsRecordMapper;

    @Autowired
    private SmsThirdChannelMapper smsThirdChannelMapper;

    @Autowired
    private ThirdChannelContainer thirdChannelContainer;

    @Override
    public void singleSend(SmsInfoDTO smsInfoDTO) {
        // todo 参数校验 1.数据校验 2.接口幂等性校验

        //路由短信发送通道
        SmsThirdChannelEntity smsThirdChannelEntity = routeService.route(smsInfoDTO.getBussinessType(), smsInfoDTO.getSmsType(), smsInfoDTO.getContentType());

        if (smsThirdChannelEntity == null) {
            throw new SLException("短信通道不存在");
        }

        //获取service
        ThirdSmsService thirdSmsService = thirdChannelContainer.get(smsThirdChannelEntity.getChannelCode());
        if (thirdSmsService == null) {
            throw new SLException("短信通道不存在");
        }
        long batchId = IdWorker.getId();

        List<SmsRecordEntity> smsRecordEntities = packageEntity(smsInfoDTO, smsThirdChannelEntity.getChannelCode(), batchId);
        log.info("smsRecordEntities : {}", smsRecordEntities);

        //入库
        if (smsRecordMapper.batchInsert(smsRecordEntities) <= 0) { //入库失败
            throw new SLException("发送失败");
        }
        //数据封装
        PlatformSmsInfoDTO platformSmsInfoDTO = packageDTO(batchId, smsInfoDTO, smsThirdChannelEntity);

        //发送短信
        thirdSmsService.send(platformSmsInfoDTO);
    }

    /**
     * 数据封装
     *
     * @return
     */
    private PlatformSmsInfoDTO packageDTO(Long batchId, SmsInfoDTO smsInfoDTO, SmsThirdChannelEntity smsThirdChannelEntity) {

        PlatformSmsInfoDTO platformSmsInfoDTO = new PlatformSmsInfoDTO();
        platformSmsInfoDTO.setId(batchId); // 短信发送唯一id,也就是批次id
        platformSmsInfoDTO.setMobiles(smsInfoDTO.getMobiles()); //发送手机号
        platformSmsInfoDTO.setContent(String.format(smsThirdChannelEntity.getSmsTemplate(), smsInfoDTO.getVerifyCode())); //短信验证码
        platformSmsInfoDTO.setSignName(smsThirdChannelEntity.getSignName()); //签名
        platformSmsInfoDTO.setThirdTemplateCode(smsThirdChannelEntity.getThirdTemplateCode()); //第三方模板code
        platformSmsInfoDTO.setUserId(smsInfoDTO.getUserId()); //发送短信用户id
        platformSmsInfoDTO.setVerifyCode(smsInfoDTO.getVerifyCode());

        return platformSmsInfoDTO;
    }

    private List<SmsRecordEntity> packageEntity(SmsInfoDTO smsInfoDTO, String channelCode, Long batchId) {

        List<SmsRecordEntity> smsRecordEntities = new ArrayList<>();
        for (String mobile : smsInfoDTO.getMobiles().split(",")) {
            SmsRecordEntity smsRecordEntity = ObjectUtils.convert(smsInfoDTO, SmsRecordEntity.class, (infoDTO, entity) -> {
                entity.setThirdChannelCode(channelCode);
                entity.setId(IdWorker.getId());
                entity.setCreater(infoDTO.getUserId());
                entity.setUpdater(infoDTO.getUserId());
                entity.setBatchId(batchId);
                entity.setMobile(mobile);
                entity.setCreated(LocalDateTime.now());
                entity.setUpdated(LocalDateTime.now());
            });
            smsRecordEntities.add(smsRecordEntity);
        }
        return smsRecordEntities;
    }

}
