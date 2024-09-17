package com.sl.transport.info.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.sl.transport.common.exception.SLException;
import com.sl.transport.info.config.RedisConfig;
import com.sl.transport.info.domain.TransportInfoDTO;
import com.sl.transport.info.entity.TransportInfoDetail;
import com.sl.transport.info.entity.TransportInfoEntity;
import com.sl.transport.info.enums.ExceptionEnum;
import com.sl.transport.info.service.BloomFilterService;
import com.sl.transport.info.service.TransportInfoService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 物流信息服务实现类
 */
@Service
public class TransportInfoServiceImpl implements TransportInfoService {

    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private Cache<String, TransportInfoDTO> transportInfoCache;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private BloomFilterService bloomFilterService;

    @Override
    @CachePut(value = "transport-info", key = "#p0") //更新缓存数据
    public TransportInfoEntity saveOrUpdate(String transportOrderId, TransportInfoDetail infoDetail) {
        //根据运单id查询
        Query query = Query.query(Criteria.where("transportOrderId").is(transportOrderId)); //构造查询条件
        TransportInfoEntity transportInfoEntity = this.mongoTemplate.findOne(query, TransportInfoEntity.class);
        if (ObjectUtil.isEmpty(transportInfoEntity)) {
            //运单信息不存在，新增数据
            transportInfoEntity = new TransportInfoEntity();
            transportInfoEntity.setTransportOrderId(transportOrderId);
            transportInfoEntity.setInfoList(ListUtil.toList(infoDetail));
            transportInfoEntity.setCreated(System.currentTimeMillis());

            //写入到布隆过滤器中
            this.bloomFilterService.add(transportOrderId);
        } else {
            //运单信息存在，只需要追加物流详情数据
            transportInfoEntity.getInfoList().add(infoDetail);
        }
        //无论新增还是更新都要设置更新时间
        transportInfoEntity.setUpdated(System.currentTimeMillis());

        //清除缓存中的数据
        // this.transportInfoCache.invalidate(transportOrderId);
        //发布订阅消息到redis
        this.stringRedisTemplate.convertAndSend(RedisConfig.CHANNEL_TOPIC, transportOrderId);

        //保存/更新到MongoDB
        return this.mongoTemplate.save(transportInfoEntity);
    }

    @Override
    @Cacheable(value = "transport-info", key = "#p0") //新增缓存数据
    public TransportInfoEntity queryByTransportOrderId(String transportOrderId) {
        //根据运单id查询
        Query query = Query.query(Criteria.where("transportOrderId").is(transportOrderId)); //构造查询条件
        TransportInfoEntity transportInfoEntity = this.mongoTemplate.findOne(query, TransportInfoEntity.class);
        if (ObjectUtil.isNotEmpty(transportInfoEntity)) {
            return transportInfoEntity;
        }
        throw new SLException(ExceptionEnum.NOT_FOUND);
    }
}
