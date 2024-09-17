package com.sl.ms.base.domain.truck;

import lombok.Data;

import java.io.Serializable;

/**
 * TransportTripsDto
 */
@Data
public class TransportTripsTruckDriverDto implements Serializable {
    private static final long serialVersionUID = -477835165829525987L;
    /**
     * id
     */
    private Long id;
    /**
     * 车辆id
     */
    private Long truckId;
    /**
     * 车次id
     */
    private Long transportTripsId;

    /**
     * 司机Id
     */
    private Long driverId;
}