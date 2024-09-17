package com.sl.ms.sms.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sl.ms.sms.entity.SmsRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 短信发送记录
 * </p>
 *
 */
@Mapper
@TableName("sms_record")
public interface SmsRecordMapper extends BaseMapper<SmsRecordEntity> {

    int batchInsert(@Param("entities")List<SmsRecordEntity> entities);
}
