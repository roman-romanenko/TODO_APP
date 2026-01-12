package org.example.todo_app.dto;

import org.example.todo_app.model.TodoStatus;

public record TodoDTO(String description, TodoStatus status) {
}
