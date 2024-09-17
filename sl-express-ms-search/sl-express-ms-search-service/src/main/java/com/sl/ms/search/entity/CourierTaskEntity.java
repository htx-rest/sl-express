package com.sl.ms.search.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "courier_task")
public class CourierTaskEntity {
    /**
     * 取派件任务id
     */
    @Id
    @Field(type = FieldType.Long)
    private Long id;

    /**
     * 订单id
     */
    @Field(type = FieldType.Long)
    private Long orderId;

    /**
     * 运单id
     */
    @Field(type = FieldType.Keyword)
    private String transportOrderId;

    /**
     * 电话
     */
    @Field(type = FieldType.Keyword)
    private String phone;

    /**
     * 姓名
     */
    @Field(type = FieldType.Keyword)
    private String name;

    /**
     * 任务类型
     */
    @Field(type = FieldType.Keyword)
    private Integer taskType;

    /**
     * 任务状态
     */
    @Field(type = FieldType.Keyword)
    private Integer status;

    /**
     * 快递员id
     */
    @Field(type = FieldType.Long)
    private Long courierId;

    /**
     * 机构id
     */
    @Field(type = FieldType.Long)
    private Long agencyId;

    /**
     * 地址
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String address;

    /**
     * 预计开始时间
     */
    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime estimatedStartTime;

    /**
     * 实际开始时间
     */
    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime actualStartTime;

    /**
     * 预计结束时间
     */
    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime estimatedEndTime;

    /**
     * 实际结束时间
     */
    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime actualEndTime;

    /**
     * 是否删除
     */
    @Field(type = FieldType.Keyword)
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime created;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updated;
}
