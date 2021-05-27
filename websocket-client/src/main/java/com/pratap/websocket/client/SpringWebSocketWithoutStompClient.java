package com.pratap.websocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class SpringWebSocketWithoutStompClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringWebSocketWithoutStompClient.class);

    @PostConstruct
    public void connect(){

        WebSocketClient webSocketClient = new StandardWebSocketClient();

        try {
            WebSocketSession webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler(){
                @Override
                public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                    LOGGER.info("connection established, session: {}", session);
                }

                @Override
                protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                    LOGGER.info("recieved message: {}",message.getPayload());
                }

                @Override
                public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
                    LOGGER.info("session status {}", status);
                }
            }, new WebSocketHttpHeaders(), URI.create("ws://localhost:8081/name")).get();

            newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
                try {
                    TextMessage message = new TextMessage("Hello !!");
                    webSocketSession.sendMessage(message);
                    LOGGER.info("sent message - " + message.getPayload());
                } catch (Exception e) {
                    LOGGER.error("Exception while sending a message", e);
                }
            }, 1, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
