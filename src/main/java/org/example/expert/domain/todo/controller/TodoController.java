package org.example.expert.domain.todo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.*;
import org.example.expert.domain.todo.dto.response.TodoFindResponse;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<TodoSaveResponse> saveTodo(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody TodoSaveRequest todoSaveRequest
    ) {
        return ResponseEntity.ok(todoService.saveTodo(authUser, todoSaveRequest));
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(todoService.getTodos(page, size));
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable long todoId) {
        return ResponseEntity.ok(todoService.getTodo(todoId));
    }

    @GetMapping("/todos/findweather")
    public ResponseEntity<Page<TodoResponse>> getTodoByWeather(@RequestBody TodoFindWeatherRequest todoFindWeatherRequest,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(todoService.getTodoByWeather(todoFindWeatherRequest, page, size));
    }


    @GetMapping("/todos/modifiedat")
    public ResponseEntity<Page<TodoResponse>> getTodoByModifiedAt(@RequestBody TodoFindDateRequest todoFindDateRequest,
                                                                  @RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(todoService.getTodoByModifiedAt(todoFindDateRequest, page, size));
    }

    @GetMapping("/todos/findtitle")
    public ResponseEntity<Page<TodoFindResponse>> getTodoByTitle(@RequestBody TodoFindTitleRequest todoFindTitleRequest,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(todoService.getTodoByTitle(todoFindTitleRequest, page, size));
    }

    @GetMapping("/todos/finddate")
    public ResponseEntity<Page<TodoFindResponse>> getTodoByDate(@RequestBody TodoFindDateRequest todoFindDateRequest,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(todoService.getTodoByDate(todoFindDateRequest, page, size));
    }

    @GetMapping("/todos/findname")
    public ResponseEntity<Page<TodoFindResponse>> getTodoByNickname(@RequestBody TodoFindNicknameRequest todoFindNicknameRequest,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(todoService.getTodoByNickname(todoFindNicknameRequest, page, size));
    }
}
