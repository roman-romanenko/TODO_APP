package org.example.todo_app.controller;

import lombok.AllArgsConstructor;
import org.example.todo_app.dto.TodoDTO;
import org.example.todo_app.model.Todo;
import org.springframework.web.bind.annotation.*;
import org.example.todo_app.service.TodoService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService service;

    @GetMapping
    public List<Todo> getAllTodos() {
        return service.getAllTodos();
    }

    @GetMapping("/{id}")
    public Optional<Todo> getTodoById(@PathVariable String id) {
        return service.getTodoById(id);
    }

    @PostMapping
    public Todo createTodo(@RequestBody TodoDTO dto) {
        return service.createTodo(dto);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody TodoDTO dto) {
        return service.updateTodo(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable String id) {
        service.delete(id);
    }


}
