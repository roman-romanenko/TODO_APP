package org.example.todo_app.model.chatGpt;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatGPTRequest(String model, List<ChatGPTMessage> messages) {
}
