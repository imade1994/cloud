
package com.tj.cloud.feign.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @AUTHOR:taoJun
 * @Date:2024/7/5
 * @Description:
 * @version:1.0
 */
@Order(-1)
@Configuration
@RequiredArgsConstructor
public class GlobalExceptionConfiguration implements ErrorWebExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionConfiguration.class);

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // header set
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(((ResponseStatusException) ex).getStatus());
        }

        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                assert ex instanceof ResponseStatusException;
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(((ResponseStatusException) ex).getReason()));
            } catch (JsonProcessingException e) {
                log.warn("Error writing response", ex);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
