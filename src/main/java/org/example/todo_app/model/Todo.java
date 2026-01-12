package org.example.todo_app.model;

import lombok.With;

public record Todo(String id, @With String description, @With TodoStatus status) {
}
