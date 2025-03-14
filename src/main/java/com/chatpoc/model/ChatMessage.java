package com.chatpoc.model;

import lombok.*;

@Getter
@Setter
@Builder
public class ChatMessage {

    private String sender;       // Nom de l'expéditeur du message
    private String content;      // Contenu du message
    private MessageType type;    // Type de message (CHAT, JOIN, LEAVE)

    /**
     * Enumération des types de messages possibles.
     */
    public enum MessageType {
        CHAT,  // Message de chat normal
        JOIN,  // Message indiquant qu'un utilisateur a rejoint le chat
        LEAVE  // Message indiquant qu'un utilisateur a quitté le chat
    }
}
