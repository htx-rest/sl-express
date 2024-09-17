package com.sl.ms.web.customer.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {
    private String openid;
//    private String avatar;
//    private String name;
@ApiModelProperty("token")
    private String token;
    @ApiModelProperty("是否绑定手机号 0否 1是")
    private Integer binding;

}
