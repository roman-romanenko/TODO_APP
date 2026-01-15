package org.example.todo_app.model.chatGpt;

import lombok.Getter;

@Getter
public enum ChatGPTMessageRole {
    DEVELOPER("developer"),
    USER("user");
    private final String value;

    ChatGPTMessageRole(String value) {
        this.value = value;
    }
}
