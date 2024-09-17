package com.sl.ms.web.courier.config;

import com.sl.transport.common.properties.RealNameVerifyProperties;
import com.sl.transport.common.util.RealNameVerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RealNameVerifyConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RealNameVerifyUtil realNameVerifyUtil(RealNameVerifyProperties realNameVerifyProperties) {
        log.info("实名认证工具类...");

        return new RealNameVerifyUtil(
                realNameVerifyProperties.getUrl(),
                realNameVerifyProperties.getAppCode());
    }
}
