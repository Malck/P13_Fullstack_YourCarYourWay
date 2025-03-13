package com.chatpoc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket configuration for the chat application.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Registers the STOMP endpoints.
     * @param registry the registry to add endpoints to.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();  // Endpoint for WebSocket connections with fallback to SockJS
    }

    /**
     * Configures the message broker.
     * @param registry the registry to configure.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");  // Prefix for messages bound for @MessageMapping methods
        registry.enableSimpleBroker("/topic");               // Enables a simple in-memory message broker for topic subscriptions
    }
}
