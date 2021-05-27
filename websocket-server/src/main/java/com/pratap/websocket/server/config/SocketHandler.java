package com.pratap.websocket.server.config;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * override the protocol to handle the text message.
 * Our handler will be capable of handling Json message.
 * This class records all the session once any web-socket connection is established
 * and broadcasts the message to all the sessions once any message is received.
 */
@Component
public class SocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        LOGGER.info("Going to execute handleTextMessage() with session \n{} and message \n{}", session, message);
//        for (WebSocketSession webSocketSession : sessions) {
//            Map map = new Gson().fromJson(message.getPayload(), Map.class);
//            webSocketSession.sendMessage(new TextMessage("Hello " + map.get("name") + " !"));
//        }

        session.sendMessage(new TextMessage("Hello  Client !!"));
        LOGGER.info("complete execution");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        LOGGER.info("Going to execute afterConnectionEstablished with session \n{}", session);
        //the messages will be broadcasted to all users.
        sessions.add(session);

        LOGGER.info("Total sessions \n{}", sessions);
    }
}
