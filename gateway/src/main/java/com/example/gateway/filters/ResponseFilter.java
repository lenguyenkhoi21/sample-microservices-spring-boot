package com.example.gateway.filters;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseFilter {
    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    private final Tracer tracer;

    public ResponseFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) ->
                chain.filter(exchange)
                     .then(Mono.fromRunnable(() -> {
                            String traceId = tracer.currentSpan().context().traceIdString();
                            logger.debug("Adding the correlation id to the outbound headers. {}", traceId);
                            exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, traceId);
                            logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
                        }
                    ));
    }
}
