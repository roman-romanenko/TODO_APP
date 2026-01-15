package org.example.todo_app.controller;

import org.example.todo_app.model.Todo;
import org.example.todo_app.model.TodoStatus;
import org.example.todo_app.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void cleanUp() {
        todoRepository.deleteAll();
    }

    @Test
    @DisplayName("getAllTodos should return list with Todos")
    void getAllTodos() throws Exception {
        Todo t1 = new Todo("1", "Test", TodoStatus.DONE);
        Todo t2 = new Todo("2", "Test2", TodoStatus.OPEN);
        todoRepository.save(t1);
        todoRepository.save(t2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
            .andExpect(status().isOk())
            .andExpect(content().json("""
                     [
                         {
                          "id": "1",
                          "description": "Test",
                          "status": "DONE"
                         },
                         {
                          "id": "2",
                          "description": "Test2",
                          "status": "OPEN"
                         }
                     ]
                    """));

    }

    @Test
    @DisplayName("getTodoById should return Todo by Id")
    void getTodoById() throws Exception {
        //GIVEN
        String todoId = "1";
        Todo t1 = new Todo(todoId, "Test", TodoStatus.DONE);
        todoRepository.save(t1);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + todoId))
        //THEN
            .andExpect(status().isOk())
            .andExpect(content().json("""
                      {
                          "id": "1",
                          "description": "Test",
                          "status": "DONE"
                      }
                    """));

    }

    @Test
    @DisplayName("getTodoById should return ErrorMessage with 404 status if Todo does not exist")
    void getTodoById_Error() throws Exception {
        //WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + "123-123-123"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                          {
                             "errorCode": 404,
                             "errorMessage": "Todo with id: 123-123-123 not found"
                           }
                        """));
    }

    @Test
    @DisplayName("createTodo should create Todo from TodoDto and return with status 201")
    void createTodo() throws Exception {
        //GIVEN
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                            "description": "Test",
                                            "status": "DONE"
                                 }
                        """))
        //THEN
            .andExpect(status().isCreated())
            .andExpect(content().json("""
                               {
                                          "description": "Test",
                                          "status": "DONE"
                               }
                       """))
            .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("update should update existing Todo from TodoDto and return")
    void updateTodo() throws Exception {
        //GIVEN
        String todoId = "1";
        Todo t1 = new Todo(todoId, "Test", TodoStatus.DONE);
        todoRepository.save(t1);
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/todo/" + todoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "description": "Updated Test",
                                "status": "DONE"
                             }
                """))
        //THEN
            .andExpect(status().isOk())
            .andExpect(content().json("""
                         {
                            "description": "Updated Test",
                            "status": "DONE"
                         }
            """))
            .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("deleteTodo should delete existing Todo By id with HTTP status 204")
    void deleteTodo() throws Exception {
        //GIVEN
        String todoId = "1";
        Todo t1 = new Todo(todoId, "Test", TodoStatus.DONE);
        todoRepository.save(t1);
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/" + todoId))
        //THEN
            .andExpect(status().isNoContent());

        assertFalse(todoRepository.existsById(todoId));
    }
}