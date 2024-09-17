package com.sl.ms.courier.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class CourierScopeDto implements Serializable {
    private static final long serialVersionUID = -7739737650020910495L;
    /**
     * id
     */
    private Long id;
    /**
     * 快递员id
     */
    private Long userId;

    /**
     * 行政区域id
     */
    private Long areaId;
    /**
     * 多边形经纬度坐标集合
     */
    private List<List<Map>> mutiPoints;

}
