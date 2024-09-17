package com.sl.transport.info.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.sl.transport.common.exception.SLException;
import com.sl.transport.info.domain.TransportInfoDTO;
import com.sl.transport.info.entity.TransportInfoEntity;
import com.sl.transport.info.enums.ExceptionEnum;
import com.sl.transport.info.service.BloomFilterService;
import com.sl.transport.info.service.TransportInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "物流信息")
@RestController
@RequestMapping("infos")
public class TransportInfoController {

    @Resource
    private TransportInfoService transportInfoService;
    @Resource
    private Cache<String, TransportInfoDTO> transportInfoCache;
    @Resource
    private BloomFilterService bloomFilterService;

    /**
     * 根据运单id查询运单信息
     *
     * @param transportOrderId 运单号
     * @return 运单信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transportOrderId", value = "运单id")
    })
    @ApiOperation(value = "查询", notes = "根据运单id查询物流信息")
    @GetMapping("{transportOrderId}")
    public TransportInfoDTO queryByTransportOrderId(@PathVariable("transportOrderId") String transportOrderId) {
        //如果布隆过滤器中不存在，无需缓存命中，直接返回即可
        boolean contains = this.bloomFilterService.contains(transportOrderId);
        if (!contains) {
            throw new SLException(ExceptionEnum.NOT_FOUND);
        }
        TransportInfoDTO transportInfoDTO = transportInfoCache.get(transportOrderId, id -> {
            //未命中，查询MongoDB
            TransportInfoEntity transportInfoEntity = this.transportInfoService.queryByTransportOrderId(id);
            //转化成DTO
            return BeanUtil.toBean(transportInfoEntity, TransportInfoDTO.class);
        });

        if (ObjectUtil.isNotEmpty(transportInfoDTO)) {
            return transportInfoDTO;
        }
        throw new SLException(ExceptionEnum.NOT_FOUND);
    }

}
