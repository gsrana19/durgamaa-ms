package com.durgamandir.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class SessionCookieFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        
        // Modify headers BEFORE the response is committed
        response.beforeCommit(() -> {
            HttpHeaders headers = response.getHeaders();
            
            // Add Set-Cookie and Authorization to Access-Control-Expose-Headers
            // This is required for browser to access these headers in CORS requests
            List<String> exposeHeaders = headers.getAccessControlExposeHeaders();
            if (exposeHeaders == null || exposeHeaders.isEmpty()) {
                headers.setAccessControlExposeHeaders(Arrays.asList("Set-Cookie", "Authorization"));
            } else if (!exposeHeaders.contains("Set-Cookie") || !exposeHeaders.contains("Authorization")) {
                // Add to existing expose headers if not already present
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Set-Cookie");
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization");
            }
            
            return Mono.empty();
        });
        
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Run early in the filter chain
    }
}

