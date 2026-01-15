package org.example.todo_app.controller;

import lombok.AllArgsConstructor;
import org.example.todo_app.dto.TodoDTO;
import org.example.todo_app.model.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.todo_app.service.TodoService;

import java.util.List;

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
    public Todo getTodoById(@PathVariable String id) {
        return service.getTodoById(id);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody TodoDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createTodo(dto));
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody TodoDTO dto) {
        return service.updateTodo(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
