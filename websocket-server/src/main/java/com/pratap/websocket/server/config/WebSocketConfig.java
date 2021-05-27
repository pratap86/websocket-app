package com.pratap.websocket.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        LOGGER.info("Going to execute registerWebSocketHandlers with WebSocketHandlerRegistry \n{}", registry);
        registry.addHandler(new SocketHandler(), "/name");
        LOGGER.info("Executed registry \n{}", registry);
    }
}
