package com.sl.ms.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sl.ms.oms.entity.OrderCargoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 货品总重量  Mapper 接口
 */
@Mapper
public interface OrderCargoMapper extends BaseMapper<OrderCargoEntity> {

    int deleteByPrimaryKey(String id);

    int insertSelective(OrderCargoEntity record);

    OrderCargoEntity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderCargoEntity record);

    int updateByPrimaryKeyWithBLOBs(OrderCargoEntity record);

    int updateByPrimaryKey(OrderCargoEntity record);

}
