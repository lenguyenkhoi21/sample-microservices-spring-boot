package com.example.gateway.filters;

import brave.Span;
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


    /*
    * The code that error, link: https://github.com/spring-cloud/spring-cloud-sleuth/issues/2049#issuecomment-955118966
    *
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
    }*/

    /*The code I do myself */

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) ->
                chain.filter(exchange)
                     .then(Mono.fromRunnable(() -> {
                        Span span = tracer.currentSpan();
                        String traceId = null;
                        if (span != null) {
                            traceId  = span.context().traceIdString();
                            logger.debug("tracer.currentSpan() not null {}", traceId);
                        } else {
                            Span newSpan = tracer.newTrace();
                            traceId = newSpan.context().traceIdString();
                            logger.debug("Create new span to get new traceId {}", traceId);
                        }
                        logger.debug("Adding the correlation id to the outbound headers. {}", traceId);
                        exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, traceId);
                        logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
                     }
                     ));
    }
}
