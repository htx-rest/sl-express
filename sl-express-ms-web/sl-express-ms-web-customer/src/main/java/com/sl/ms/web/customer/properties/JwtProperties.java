package com.sl.ms.web.customer.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sl.jwt")
@Data
public class JwtProperties {
    private String userSecretKey;


    private Integer userTtl; // token过期时间 单位 小时
}
