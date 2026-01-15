package org.example.todo_app.service;

import org.bson.json.JsonParseException;
import org.example.todo_app.dto.TodoDTO;
import org.example.todo_app.exceptions.NotFoundException;
import org.example.todo_app.exceptions.TodoOrthographicException;
import org.example.todo_app.model.Todo;
import org.example.todo_app.model.chatGpt.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.example.todo_app.repository.TodoRepository;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repo;
    private final RestClient restClient;

    private static final ChatGPTMessage DEVELOPER_MESSAGE =
            ChatGPTMessage.builder()
                    .role(ChatGPTMessageRole.DEVELOPER.getValue())
                    .content("""
                        Please check the following text for spelling and orthographic errors.
                        The text may be written in any language.
                        Return the result only as a JSON object with the following structure:
                        {
                          "statusCheck": "ok" | "error",
                          "suggestion": ""
                        }
                        Rules:
                        If no spelling errors are found, set "statusCheck" to "ok" and "suggestion" to an empty string.
                        If spelling errors are found, set "statusCheck" to "error" and "suggestion" to a corrected version of the text.
                        Do not include explanations, comments, or additional text outside the JSON object.
                    """)
                    .build();

    public TodoService(TodoRepository repo,
                       RestClient.Builder restClientBuilder,
                       @Value("${OPEN_AI_API_KEY}") String token) {
        this.repo = repo;
        this.restClient = restClientBuilder
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Todo createTodo(TodoDTO dto) {
        ChatGPTRequest requestBody = buildRequest(dto.description());
        ChatGPTResponse response = sendRequest(requestBody);

        String responseContent = response.choices().getFirst().message().content();

        ChatGPTResponseContent parsedResponse = parseChatGptResponse(responseContent);

        if("error".equalsIgnoreCase(parsedResponse.statusCheck())) {
            throw new TodoOrthographicException(
                    "Orthographic errors detected. Suggested correction: " + parsedResponse.suggestion());
        }

        return repo.save(new Todo(null, dto.description(), dto.status()));
    }

    private ChatGPTResponseContent parseChatGptResponse(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, ChatGPTResponseContent.class);
        } catch (Exception e) {
            throw new JsonParseException( "Invalid JSON from ChatGPT: " + content );
        }
    }

    private ChatGPTRequest buildRequest(String requestContent) {
        ChatGPTMessage userMessage = ChatGPTMessage.builder()
                .role(ChatGPTMessageRole.USER.getValue())
                .content(requestContent)
                .build();

        return ChatGPTRequest.builder()
                .model("gpt-5-nano-2025-08-07")
                .messages(List.of(DEVELOPER_MESSAGE, userMessage))
                .build();
    }

    private ChatGPTResponse sendRequest(ChatGPTRequest requestBody) {
        return restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(ChatGPTResponse.class);
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
