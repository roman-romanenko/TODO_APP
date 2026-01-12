package controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import service.TodoService;

@Repository
@RequestMapping("/api/todo")
@AllArgsConstructor
public class TodoController {
    private final TodoService service;

}
