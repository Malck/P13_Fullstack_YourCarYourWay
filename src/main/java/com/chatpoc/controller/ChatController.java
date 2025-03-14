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
     * Reçoit un message de chat et le renvoie à tous les clients abonnés au topic public.
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage; // Renvoie le message tel quel à tous les clients
    }

    /**
     * Ajoute un nouvel utilisateur au chat et envoie un message de bienvenue.
     */
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Ajoute l'utilisateur à la session
        Objects.requireNonNull(headerAccessor.getSessionAttributes())
               .put("username", chatMessage.getSender());

        // Modifie le message pour indiquer que l'utilisateur a rejoint
        chatMessage.setContent(chatMessage.getSender() + " a rejoint le chat !");
        chatMessage.setType(ChatMessage.MessageType.JOIN);

        return chatMessage; // Renvoie le message de bienvenue à tous les clients
    }
}