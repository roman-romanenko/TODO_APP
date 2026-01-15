package org.example.todo_app.service;

import org.example.todo_app.dto.TodoDTO;
import org.example.todo_app.model.Todo;
import org.example.todo_app.model.TodoStatus;
import org.example.todo_app.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository mockRepo;
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = new TodoService(mockRepo);
    }

    @Test
    @DisplayName("getAllTodos should return List of Todos")
    void getAllTodos() {
        //GIVEN
        Todo todo = new Todo("1", "todo1", TodoStatus.OPEN);

        when(mockRepo.findAll()).thenReturn(List.of(todo));
        //WHEN
        List<Todo> todos = todoService.getAllTodos();
        //THEN
        assertEquals(List.of(todo), todos);
        verify(mockRepo).findAll();
    }

    @Test
    @DisplayName("createTodo should create and return new Todo from TodoDTO")
    void createTodo() {
        //GIVEN
        TodoDTO dto = new TodoDTO("Test", TodoStatus.OPEN);
        Todo todoFromDTO = new Todo(null, dto.description(), dto.status());

        when(mockRepo.save(todoFromDTO)).thenReturn(todoFromDTO);
        //WHEN
        Todo createdTodo = todoService.createTodo(dto);
        //THEN
        assertEquals(dto.description(), createdTodo.description());
        assertEquals(dto.status(), createdTodo.status());
        verify(mockRepo).save(todoFromDTO);
    }

    @Test
    @DisplayName("getTodoById should return Todo by Id")
    void getTodoById() {
        //GIVEN
        String id = "1";
        Todo todo = new Todo(id, "todo1", TodoStatus.OPEN);

        when(mockRepo.findById(id)).thenReturn(Optional.of(todo));
        //WHEN
        Todo existingTodo = todoService.getTodoById(id);
        //THEN
        assertEquals(todo,  existingTodo);
        verify(mockRepo).findById(id);
    }

    @Test
    @DisplayName("updateTodo should update Todo from TodoDTO")
    void updateTodo() {
        //GIVEN
        String id = "1";
        Todo originalTodo = new Todo(id, "todo1", TodoStatus.OPEN);
        TodoDTO dto = new TodoDTO("Test", TodoStatus.DONE);
        Todo updatedTodo = originalTodo
                .withDescription(dto.description())
                .withStatus(dto.status());

        when(mockRepo.findById(id)).thenReturn(Optional.of(originalTodo));
        when(mockRepo.save(updatedTodo)).thenReturn(updatedTodo);
        //WHEN
        Todo result = todoService.updateTodo(id, dto);

        //THEN
        assertEquals(dto.description(), result.description());
        assertEquals(dto.status(), result.status());
        verify(mockRepo).findById(id);
        verify(mockRepo).save(updatedTodo);
    }

    @Test
    @DisplayName("updateTodo should throw Exception when Todo with ID not exist")
    void updateTodo_Exception() {
        //GIVEN
        String id = "99";
        TodoDTO dto = new TodoDTO("Test", TodoStatus.DONE);

        when(mockRepo.findById(id)).thenReturn(Optional.empty());
        //WHEN
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> todoService.updateTodo(id, dto)
        );
        //THEN
        assertEquals("Todo with id: " +  id + " not found.", exception.getMessage());
        verify(mockRepo).findById(id);
    }


    @Test
    @DisplayName("delete should delete Todo by Id")
    void delete() {
        //GIVEN
        String id = "1";
        //WHEN
        todoService.delete(id);
        //THEN
        verify(mockRepo).deleteById(id);
    }
}