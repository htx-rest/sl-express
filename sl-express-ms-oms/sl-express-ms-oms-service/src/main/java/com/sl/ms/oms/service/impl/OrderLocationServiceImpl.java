package com.sl.ms.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sl.ms.oms.entity.OrderLocationEntity;
import com.sl.ms.oms.mapper.OrderLocationMapper;
import com.sl.ms.oms.service.OrderLocationService;
import org.springframework.stereotype.Service;

/**
 * 位置信息服务实现
 */
@Service
public class OrderLocationServiceImpl extends ServiceImpl<OrderLocationMapper, OrderLocationEntity>
        implements OrderLocationService {

}
