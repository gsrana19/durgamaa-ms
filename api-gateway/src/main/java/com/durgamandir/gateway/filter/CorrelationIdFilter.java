package com.durgamandir.gateway.filter;

import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Global filter for API Gateway to generate and propagate correlation ID.
 * Generates a correlation ID if not present in the request header.
 * Adds it to MDC and forwards it to downstream services via X-Correlation-Id header.
 */
@Component
public class CorrelationIdFilter implements GlobalFilter, Ordered {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";
    private static final String TRACE_ID_MDC_KEY = "traceId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        // Get correlation ID from header or generate a new one
        String correlationId = request.getHeaders().getFirst(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }

        // Add to MDC
        MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
        MDC.put(TRACE_ID_MDC_KEY, correlationId);
        
        // Extract path and method for logging
        String path = request.getPath().value();
        String method = request.getMethod() != null ? request.getMethod().name() : "";
        MDC.put("path", path);
        MDC.put("method", method);

        // Forward correlation ID to downstream services
        ServerHttpRequest modifiedRequest = request.mutate()
                .header(CORRELATION_ID_HEADER, correlationId)
                .build();

        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(modifiedRequest)
                .build();

        return chain.filter(modifiedExchange)
                .doFinally(signalType -> {
                    // Clean up MDC after request processing
                    MDC.clear();
                });
    }

    @Override
    public int getOrder() {
        // High precedence to ensure correlation ID is set early
        return Ordered.HIGHEST_PRECEDENCE;
    }
}




