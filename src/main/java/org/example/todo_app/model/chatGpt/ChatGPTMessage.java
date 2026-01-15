package org.example.todo_app.model.chatGpt;

import lombok.Builder;

@Builder
public record ChatGPTMessage(String role, String content) {
}
