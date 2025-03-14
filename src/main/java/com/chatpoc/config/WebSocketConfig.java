package com.chatpoc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // Active la gestion des messages WebSocket avec un broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Définit l'endpoint WebSocket "/ws" avec un fallback SockJS pour les navigateurs non compatibles
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Préfixe pour les messages destinés aux méthodes annotées avec @MessageMapping
        registry.setApplicationDestinationPrefixes("/app");

        // Active un broker simple en mémoire pour les destinations préfixées par "/topic"
        registry.enableSimpleBroker("/topic");
    }
}
