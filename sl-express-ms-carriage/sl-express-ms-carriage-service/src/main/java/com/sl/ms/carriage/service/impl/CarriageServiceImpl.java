package com.sl.ms.carriage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sl.ms.base.api.common.AreaFeign;
import com.sl.ms.carriage.domain.constant.CarriageConstant;
import com.sl.ms.carriage.domain.dto.CarriageDTO;
import com.sl.ms.carriage.domain.dto.WaybillDTO;
import com.sl.ms.carriage.domain.enums.EconomicRegionEnum;
import com.sl.ms.carriage.entity.CarriageEntity;
import com.sl.ms.carriage.enums.CarriageExceptionEnum;
import com.sl.ms.carriage.mapper.CarriageMapper;
import com.sl.ms.carriage.service.CarriageService;
import com.sl.ms.carriage.utils.CarriageUtils;
import com.sl.transport.common.exception.SLException;
import com.sl.transport.common.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarriageServiceImpl extends ServiceImpl<CarriageMapper, CarriageEntity> implements CarriageService {

    @Resource
    private AreaFeign areaFeign;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    //模板缓存key
    private static final String CACHE_KEY = "CARRIAGE_CACHE";

    @Override
    public CarriageDTO saveOrUpdate(CarriageDTO carriageDto) {
        log.info("新增运费模板 --> {}", carriageDto);
        // 校验运费模板是否存在，如果不存在直接插入
        // 如果存在的话需要判断此次插入的是否为经济区互寄，非经济区互寄是不可以重复的
        // 如果是经济区互寄，需要判断所关联的城市是否重复，如果重复则保存失败
        LambdaQueryWrapper<CarriageEntity> queryWrapper = Wrappers.<CarriageEntity>lambdaQuery()
                .eq(CarriageEntity::getTemplateType, carriageDto.getTemplateType())
                .eq(CarriageEntity::getTransportType, carriageDto.getTransportType())
                // 如果是修改操作，需要查询出其他的模板数据，用于是否重复判定
                .ne(ObjectUtil.isNotEmpty(carriageDto.getId()), CarriageEntity::getId, carriageDto.getId());

        //查询数据库
        List<CarriageEntity> carriageList = super.list(queryWrapper);

        if (CollUtil.isEmpty(carriageList)) {
            //没有重复的模板，可以直接插入或更新操作
            return this.saveOrUpdateCarriage(carriageDto);
        }

        //校验是否为经济区
        if (ObjectUtil.notEqual(carriageDto.getTemplateType(), CarriageConstant.ECONOMIC_ZONE)) {
            //非经济区，模板重复
            throw new SLException(CarriageExceptionEnum.NOT_ECONOMIC_ZONE_REPEAT);
        }

        //如果是经济区互寄类型，需进一步判断关联城市是否重复，通过集合取交集判断是否重复
        List<String> allList = carriageList.stream().map(CarriageEntity::getAssociatedCity)
                .map(associatedCity -> StrUtil.splitToArray(associatedCity, ','))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        //取交集
        Collection<String> intersection = CollUtil.intersection(allList, carriageDto.getAssociatedCityList());
        if (CollUtil.isNotEmpty(intersection)) {
            //重复
            throw new SLException(CarriageExceptionEnum.ECONOMIC_ZONE_CITY_REPEAT);
        }

        //没有重复，可以新增或更新
        return this.saveOrUpdateCarriage(carriageDto);
    }

    /**
     * 新增或更数据到数据库
     *
     * @param carriageDTO 传入的DTO数据
     * @return 新的DTO数据
     */
    private CarriageDTO saveOrUpdateCarriage(CarriageDTO carriageDTO) {
        //将DTO转成Entity
        CarriageEntity carriageEntity = CarriageUtils.toEntity(carriageDTO);
        super.saveOrUpdate(carriageEntity);
        //转化成DTO返回
        CarriageDTO dto = CarriageUtils.toDTO(carriageEntity);

        //清除缓存
        this.stringRedisTemplate.delete(CACHE_KEY);
        return dto;
    }

    @Override
    public List<CarriageDTO> findAll() {
        // 构造查询条件，按创建时间倒序
        LambdaQueryWrapper<CarriageEntity> queryWrapper = Wrappers.<CarriageEntity>lambdaQuery()
                .orderByDesc(CarriageEntity::getCreated);

        // 查询数据库
        List<CarriageEntity> list = super.list(queryWrapper);

        // 将结果转换为DTO类型
        return list.stream().map(CarriageUtils::toDTO).collect(Collectors.toList());
    }

    @Override
    public CarriageDTO compute(WaybillDTO waybillDTO) {
        //根据参数查找运费模板
        CarriageEntity carriage;
        String redisHashKey = StrUtil.format("{}_{}", waybillDTO.getSenderCityId(), waybillDTO.getReceiverCityId());
        Object cacheData = this.stringRedisTemplate.opsForHash().get(CACHE_KEY, redisHashKey);
        if (ObjectUtil.isNotEmpty(cacheData)) {
            carriage = JSONUtil.toBean(Convert.toStr(cacheData), CarriageEntity.class);
        } else {
            //数据库查找
            carriage = this.findCarriage(waybillDTO);
            //写到缓存中
            this.stringRedisTemplate.opsForHash().put(CACHE_KEY, redisHashKey, JSONUtil.toJsonStr(carriage));
        }
        //计算重量，最小重量为1kg
        double computeWeight = this.getComputeWeight(waybillDTO, carriage);

        //计算运费，首重 + 续重
        double expense = carriage.getFirstWeight() + ((computeWeight - 1) * carriage.getContinuousWeight());

        //保留一位小数
        expense = NumberUtil.round(expense, 1).doubleValue();

        //封装运费和计算重量到DTO，并返回
        CarriageDTO carriageDTO = CarriageUtils.toDTO(carriage);
        carriageDTO.setExpense(expense);
        carriageDTO.setComputeWeight(computeWeight);
        return carriageDTO;
    }

    /**
     * 根据体积参数与实际重量计算计费重量
     *
     * @param waybillDTO 运费计算对象
     * @param carriage   运费模板
     * @return 计费重量
     */
    private double getComputeWeight(WaybillDTO waybillDTO, CarriageEntity carriage) {
        //计算体积，如果传入体积不需要计算
        Integer volume = waybillDTO.getVolume();
        if (ObjectUtil.isEmpty(volume)) {
            try {
                //长*宽*高计算体积
                volume = waybillDTO.getMeasureLong() * waybillDTO.getMeasureWidth() * waybillDTO.getMeasureHigh();
            } catch (Exception e) {
                //计算出错设置体积为0
                volume = 0;
            }
        }

        // 计算体积重量，体积 / 轻抛系数
        BigDecimal volumeWeight = NumberUtil.div(volume, carriage.getLightThrowingCoefficient(), 1);

        //取大值
        double computeWeight = NumberUtil.max(volumeWeight.doubleValue(), NumberUtil.round(waybillDTO.getWeight(), 1).doubleValue());

        //计算续重，规则：不满1kg，按1kg计费；10kg以下续重以0.1kg计量保留1位小数；10-100kg续重以0.5kg计量保留1位小数；100kg以上四舍五入取整
        if (computeWeight <= 1) {
            return 1;
        }

        if (computeWeight <= 10) {
            return computeWeight;
        }

        // 举例：
        // 108.4kg按照108kg收费
        // 108.5kg按照109kg收费
        // 108.6kg按照109kg收费
        if (computeWeight >= 100) {
            return NumberUtil.round(computeWeight, 0).doubleValue();
        }

        //0.5为一个计算单位，举例：
        // 18.8kg按照19收费，
        // 18.4kg按照18.5kg收费
        // 18.1kg按照18.5kg收费
        // 18.6kg按照19收费
        int integer = NumberUtil.round(computeWeight, 0, RoundingMode.DOWN).intValue();
        if (NumberUtil.sub(computeWeight, integer) <= 0.5) {
            return NumberUtil.add(integer, 0.5);
        }
        return NumberUtil.add(integer, 1);
    }

    /**
     * 根据参数查找运费模板
     *
     * @param waybillDTO 参数
     * @return 运费模板
     */
    private CarriageEntity findCarriage(WaybillDTO waybillDTO) {
        //运费模板优先级：同城>省内>经济区互寄>跨省
        if (ObjectUtil.equals(waybillDTO.getReceiverCityId(), waybillDTO.getSenderCityId())) {
            //同城
            CarriageEntity carriage = this.findByTemplateType(CarriageConstant.SAME_CITY);
            if (ObjectUtil.isNotEmpty(carriage)) {
                return carriage;
            }

            //省内
            carriage = this.findByTemplateType(CarriageConstant.SAME_PROVINCE);
            if (ObjectUtil.isNotEmpty(carriage)) {
                return carriage;
            }
        }

        //经济区互寄
        CarriageEntity carriage = this.findEconomicCarriage(waybillDTO);
        if (ObjectUtil.isNotEmpty(carriage)) {
            return carriage;
        }

        //跨省
        carriage = this.findByTemplateType(CarriageConstant.TRANS_PROVINCE);
        if (ObjectUtil.isNotEmpty(carriage)) {
            return carriage;
        }

        throw new SLException(CarriageExceptionEnum.NOT_FOUND);
    }

    private CarriageEntity findEconomicCarriage(WaybillDTO waybillDTO) {
        // 获取收寄件地址省份id
        Long receiverProvinceId = this.areaFeign.get(waybillDTO.getReceiverCityId()).getParentId();
        Long senderProvinceId = this.areaFeign.get(waybillDTO.getSenderCityId()).getParentId();

        //获取经济区城市配置枚举
        LinkedHashMap<String, EconomicRegionEnum> EconomicRegionMap = EnumUtil.getEnumMap(EconomicRegionEnum.class);
        EconomicRegionEnum economicRegionEnum = null;
        for (EconomicRegionEnum regionEnum : EconomicRegionMap.values()) {
            //该经济区是否全部包含收发件省id
            boolean result = ArrayUtil.containsAll(regionEnum.getValue(), receiverProvinceId, senderProvinceId);
            if (result) {
                economicRegionEnum = regionEnum;
                break;
            }
        }

        if (null == economicRegionEnum) {
            //没有找到对应的经济区
            return null;
        }

        //根据类型编码查询
        LambdaQueryWrapper<CarriageEntity> queryWrapper = Wrappers.lambdaQuery(CarriageEntity.class)
                .eq(CarriageEntity::getTemplateType, CarriageConstant.ECONOMIC_ZONE)
                .eq(CarriageEntity::getTransportType, CarriageConstant.REGULAR_FAST)
                .like(CarriageEntity::getAssociatedCity, economicRegionEnum.getCode());
        return super.getOne(queryWrapper);
    }

    /**
     * 根据模板类型查询模板
     *
     * @param templateType 模板类型：1-同城寄，2-省内寄，3-经济区互寄，4-跨省
     * @return 运费模板
     */
    private CarriageEntity findByTemplateType(Integer templateType) {
        LambdaQueryWrapper<CarriageEntity> queryWrapper = Wrappers.lambdaQuery(CarriageEntity.class)
                .eq(CarriageEntity::getTemplateType, templateType)
                .eq(CarriageEntity::getTransportType, CarriageConstant.REGULAR_FAST);
        return super.getOne(queryWrapper);
    }
}
