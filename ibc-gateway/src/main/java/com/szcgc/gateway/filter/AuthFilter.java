
package com.szcgc.gateway.filter;

import com.szcgc.comm.util.StringUtils;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.gateway.config.AuthConfig;
import com.szcgc.gateway.util.TokenUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 鉴权认证
 *
 * @author Chill
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        System.out.println("AuthFilter path " + path);
        if (isSkip(path)) {
            return chain.filter(exchange);
        }
        System.out.println("AuthFilter token check ");
        ServerHttpResponse rep = exchange.getResponse();
        String headerToken = exchange.getRequest().getHeaders().getFirst(WebConstants.AUTH_KEY);
        String paramToken = exchange.getRequest().getQueryParams().getFirst(WebConstants.AUTH_KEY);
        if (StringUtils.isBlank(headerToken) && StringUtils.isBlank(paramToken)) {
            return unAuth(rep, ERROR_NO_AUTHENTICATION);
        }
        String token = StringUtils.isEmpty(headerToken) ? paramToken : headerToken;
        String value = TokenUtil.readToken(token);
        if (value == null) {
            return unAuth(rep, ERROR_NO_AUTHORIZATION);
        }
        //return chain.filter(exchange);
        System.out.println("AuthFilter token ok " + value);
        ServerHttpRequest mutateReq = exchange.getRequest().mutate().header(WebConstants.HEADER_UID, value).build();
        ServerWebExchange mutateExchange = exchange.mutate().request(mutateReq).build();
        return chain.filter(mutateExchange);
    }

    private boolean isSkip(String path) {
        return AuthConfig.DEFAULT_SKIP_URL.stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }

    private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer buffer = resp.bufferFactory().wrap(msg.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }

    public static final String ERROR_NO_AUTHORIZATION = "{\"code\":403,\"msg\":\"没有该接口访问权限\"}";
    public static final String ERROR_NO_AUTHENTICATION = "{\"code\":401,\"msg\":\"没有登录信息\"}";

}
