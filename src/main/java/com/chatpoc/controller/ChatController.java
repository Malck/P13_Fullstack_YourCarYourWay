package com.chatpoc.controller;

import com.chatpoc.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class ChatController {

    /**
     * @param chatMessage 
     * @return the chat message.
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
    return chatMessage;
    }


    /**
     * @param chatMessage 
     * @param headerAccessor 
     * @return the chat message.
     */
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {

        Objects.requireNonNull(headerAccessor.getSessionAttributes())
               .put("username", chatMessage.getSender());
        
        return chatMessage;
    }
}
