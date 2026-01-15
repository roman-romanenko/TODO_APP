package org.example.todo_app.service;

import lombok.AllArgsConstructor;
import org.example.todo_app.dto.TodoDTO;
import org.example.todo_app.exceptions.NotFoundException;
import org.example.todo_app.model.Todo;
import org.springframework.stereotype.Service;
import org.example.todo_app.repository.TodoRepository;

import java.util.List;

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

    public Todo getTodoById(String id) {
        return repo.findById(id).orElseThrow(() ->
                new NotFoundException("Todo with id: " + id + " not found"));
    }

    public Todo updateTodo(String id, TodoDTO dto) {
        Todo existingTodo = repo.findById(id).orElseThrow(() -> new NotFoundException("Todo with id: " +  id + " not found."));

        return repo.save(existingTodo
                .withDescription(dto.description())
                .withStatus(dto.status())
        );
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
