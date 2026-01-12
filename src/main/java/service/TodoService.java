package service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import repository.TodoRepository;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository repo;

}
