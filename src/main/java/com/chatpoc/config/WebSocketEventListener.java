package com.chatpoc.config;

import lombok.RequiredArgsConstructor;
import com.chatpoc.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate; // Utilisé pour envoyer des messages WebSocket

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        // Récupère les informations de session de l'utilisateur déconnecté
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");

        if (username != null) {
            log.info("User disconnected: {}", username);

            // Crée un message pour informer que l'utilisateur a quitté le chat
            ChatMessage chatMessage = ChatMessage.builder()
                    .type(ChatMessage.MessageType.LEAVE)
                    .sender(username)
                    .content(username + " a quitté le chat !")
                    .build();

            // Envoie le message au topic public pour informer tous les clients
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}