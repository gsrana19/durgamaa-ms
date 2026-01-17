package com.durgamandir.donation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filter to extract correlation ID from request header and add it to MDC.
 * This allows all log statements to include the correlation ID for request tracing.
 */
@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String CORRELATION_ID_MDC_KEY = "correlationId";
    private static final String TRACE_ID_MDC_KEY = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // Get correlation ID from header or generate a new one (if called directly, not via gateway)
            String correlationId = request.getHeader(CORRELATION_ID_HEADER);
            if (correlationId == null || correlationId.isEmpty()) {
                correlationId = UUID.randomUUID().toString();
            }

            // Add to MDC for logging
            MDC.put(CORRELATION_ID_MDC_KEY, correlationId);
            MDC.put(TRACE_ID_MDC_KEY, correlationId);

            // Extract path and method for logging
            String path = request.getRequestURI();
            String method = request.getMethod();
            MDC.put("path", path);
            MDC.put("method", method);

            // Continue with the filter chain
            filterChain.doFilter(request, response);
        } finally {
            // Clean up MDC after request processing
            MDC.clear();
        }
    }
}



