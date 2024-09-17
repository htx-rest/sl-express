package com.sl.ms.base.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sl.ms.base.entity.base.WorkHistorySchedulingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkHistorySchedulingMapper extends BaseMapper<WorkHistorySchedulingEntity> {

    void batchInsert(@Param("entities") List<WorkHistorySchedulingEntity> workHistorySchedulingEntities);
}
