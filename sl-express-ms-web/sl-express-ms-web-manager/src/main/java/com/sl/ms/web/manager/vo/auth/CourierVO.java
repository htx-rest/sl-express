package com.sl.ms.web.manager.vo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("快递员信息")
public class CourierVO implements Serializable {
    private static final long serialVersionUID = 6567139329695314706L;

    @ApiModelProperty(value = "快递员id")
    private Long id;

    @ApiModelProperty(value = "快递员姓名")
    private String name;
}
