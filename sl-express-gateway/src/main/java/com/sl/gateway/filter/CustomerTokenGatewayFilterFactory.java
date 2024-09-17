package com.sl.gateway.filter;

import cn.hutool.core.convert.Convert;
import com.itheima.auth.sdk.dto.AuthUserInfoDTO;
import com.sl.gateway.config.MyConfig;
import com.sl.gateway.constant.JwtClaimsConstant;
import com.sl.gateway.properties.JwtProperties;
import com.sl.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户端token拦截处理
 */
@Slf4j
@Component
public class CustomerTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> implements AuthFilter {

    @Resource
    private MyConfig myConfig;

    @Resource
    private JwtProperties jwtProperties;

    @Override
    public GatewayFilter apply(Object config) {
        return new TokenGatewayFilter(this.myConfig, this);
    }

    @Override
    public AuthUserInfoDTO check(String token) {
        // 普通用户的token没有对接权限系统，需要自定实现
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Convert.toLong(claims.get(JwtClaimsConstant.USER_ID));
            //token解析成功，放行
            AuthUserInfoDTO authUserInfoDTO = new AuthUserInfoDTO();
            authUserInfoDTO.setUserId(userId);
            return authUserInfoDTO;
        } catch (Exception ex) {
            log.error("token解析失败");
            //401表示未授权，需要前端配合跳转回登录页面
            return null;
        }
    }

    @Override
    public Boolean auth(String token, AuthUserInfoDTO authUserInfoDTO, String path) {
        //普通用户不需要校验角色
        return true;
    }
}
