package com.sl.ms.work.domain.dto.response;

import com.sl.ms.work.domain.enums.transportorder.TransportOrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzj
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportOrderStatusCountDTO {

    @ApiModelProperty(value = "状态枚举", required = true)
    private TransportOrderStatus status;
    @ApiModelProperty(value = "状态编码", required = true)
    private Integer statusCode;
    @ApiModelProperty(value = "数量", required = true)
    private Long count;

}
