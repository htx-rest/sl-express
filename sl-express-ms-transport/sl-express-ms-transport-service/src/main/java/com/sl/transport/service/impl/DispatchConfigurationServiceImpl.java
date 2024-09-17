package com.sl.transport.service.impl;

import com.sl.transport.domain.DispatchConfigurationDTO;
import com.sl.transport.service.DispatchConfigurationService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 调度服务相关业务
 *
 * @author zzj
 * @version 1.0
 */
@Service
public class DispatchConfigurationServiceImpl implements DispatchConfigurationService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public DispatchConfigurationDTO findConfiguration() {
        //调度时间配置
        String dispatchTimeRedisKey = "DISPATCH_CONFIGURATION:TIME";
        String dispatchTime = stringRedisTemplate.opsForValue().get(dispatchTimeRedisKey);

        //调度方式配置
        String dispatchMethodRedisKey = "DISPATCH_CONFIGURATION:METHOD";
        String dispatchMethod = stringRedisTemplate.opsForValue().get(dispatchMethodRedisKey);

        //组装响应结果
        return DispatchConfigurationDTO.builder()
                //如果查不到调度时间，默认值为2小时
                .dispatchTime(Integer.parseInt(dispatchTime != null ? dispatchTime : "2"))
                //如果查不到调度方式，默认值为2成本最低
                .dispatchMethod(Integer.parseInt(dispatchMethod != null ? dispatchMethod : "2"))
                .build();
    }

    @Override
    public void saveConfiguration(DispatchConfigurationDTO dto) {
        //调度时间配置
        String dispatchTimeRedisKey = "DISPATCH_CONFIGURATION:TIME";
        stringRedisTemplate.opsForValue().set(dispatchTimeRedisKey, String.valueOf(dto.getDispatchTime()));

        //调度方式配置
        String dispatchMethodRedisKey = "DISPATCH_CONFIGURATION:METHOD";
        stringRedisTemplate.opsForValue().set(dispatchMethodRedisKey, String.valueOf(dto.getDispatchMethod()));
    }
}
