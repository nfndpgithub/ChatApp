package com.websocket.chat.app.controller;

import com.websocket.chat.app.model.ChatMessage;
import com.websocket.chat.app.model.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
@Slf4j
@Component
public class WebSocketEvenetListener {


    @Autowired
    private SimpMessageSendingOperations sendingOperations;
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event){
    log.info("we have a new user!");

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String username= (String) headerAccessor.getSessionAttributes().get("username");
        ChatMessage chatMessage= ChatMessage.builder().type(MessageType.DISCONNECT).sender(username).build();

        sendingOperations.convertAndSend("/topic/public", chatMessage);
    }



}
