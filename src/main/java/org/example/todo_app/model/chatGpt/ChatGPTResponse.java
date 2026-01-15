package org.example.todo_app.model.chatGpt;

import java.util.List;

public record ChatGPTResponse(List<ChatGPTChoice> choices) {
}
