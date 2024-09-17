package com.sl.ms.base.domain.truck;

import lombok.Data;

import java.io.Serializable;

/**
 * TransportTripsDto
 */
@Data
public class TruckTripsDto implements Serializable {
    private static final long serialVersionUID = -7242590031212408817L;
    /**
     * id
     */
    private Long id;
    /**
     * 车次名称
     */
    private String name;

    /**
     * 发车时间
     */
    private Integer departureTime;

    /**
     * 持续时间
     */
    private Integer estimatedTime;

    /**
     * 所属线路id
     */
    private Long transportLineId;

    /**
     * 周期，1为天，2为周，3为月
     */
    private Integer period;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
}