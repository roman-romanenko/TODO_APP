package org.example.todo_app.service;

import lombok.AllArgsConstructor;
import org.example.todo_app.dto.TodoDTO;
import org.example.todo_app.model.Todo;
import org.springframework.stereotype.Service;
import org.example.todo_app.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository repo;

    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Todo createTodo(TodoDTO dto) {
        return repo.save(new Todo(null, dto.description(), dto.status()));
    }

    public Optional<Todo> getTodoById(String id) {
        return repo.findById(id);
    }

    public Todo updateTodo(String id, TodoDTO dto) {
        Todo existingTodo = repo.findById(id).orElseThrow(() -> new RuntimeException("Todo with id: " +  id + " not found."));

        return repo.save(existingTodo
                .withDescription(dto.description())
                .withStatus(dto.status())
        );
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
