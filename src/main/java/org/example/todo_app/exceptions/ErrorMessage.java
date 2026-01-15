package org.example.todo_app.exceptions;

import lombok.Builder;

@Builder
public record ErrorMessage(int errorCode, String errorMessage) {
}
