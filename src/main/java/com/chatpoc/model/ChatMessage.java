package com.chatpoc.model;

import lombok.*;

@Getter
@Setter
@Builder
public class ChatMessage {
    private String sender;
    private String content;
    private MessageType type;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
