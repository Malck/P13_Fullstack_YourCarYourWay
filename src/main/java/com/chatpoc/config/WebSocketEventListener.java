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

/**
 * WebSocket event listener for handling WebSocket events.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * @param event the disconnection event.
     */
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
      StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
      String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");

      if (username != null) {
        log.info("User disconnected: {}", username);

        // Crée un message pour indiquer que l'utilisateur a quitté
        ChatMessage chatMessage = ChatMessage.builder()
                .type(ChatMessage.MessageType.LEAVE)
                .sender(username)
                .content(username + " a quitté le chat !")
                .build();
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}

