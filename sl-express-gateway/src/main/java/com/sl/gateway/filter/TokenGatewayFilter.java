package com.sl.gateway.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.itheima.auth.sdk.dto.AuthUserInfoDTO;
import com.sl.gateway.config.MyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class TokenGatewayFilter implements GatewayFilter, Ordered {

    private MyConfig myConfig;
    private AuthFilter authFilter;

    public TokenGatewayFilter(MyConfig myConfig, AuthFilter authFilter) {
        this.myConfig = myConfig;
        this.authFilter = authFilter;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        if (StrUtil.startWithAny(path, myConfig.getNoAuthPaths())) {
            //无需校验，直接放行
            return chain.filter(exchange);
        }

        //获取header的参数
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StrUtil.isEmpty(token)) {
            //没有权限
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //校验token
        AuthUserInfoDTO authUserInfoDTO = null;
        try{ //捕获token校验异常
            authUserInfoDTO = this.authFilter.check(token);
        }catch (Exception e){
            log.error("权限校验失败，e:",e);
        }
        if (ObjectUtil.isEmpty(authUserInfoDTO)) {
            //token失效 或 伪造
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //鉴权
        Boolean result = false;
        try { //捕获鉴权异常
            result = this.authFilter.auth(token, authUserInfoDTO, path);
        }catch (Exception e){
            log.error("鉴权失败，e:",e);
        }
        if (!result) {
            //没有权限
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

        //增加参数
        exchange.getRequest().mutate().header("userInfo", JSONUtil.toJsonStr(authUserInfoDTO));
        exchange.getRequest().mutate().header("token", token);

        //校验通过放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
