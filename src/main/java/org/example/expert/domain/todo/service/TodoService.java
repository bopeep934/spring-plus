package org.example.expert.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.dto.request.*;
import org.example.expert.domain.todo.dto.response.TodoFindResponse;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;

    @Transactional
    public TodoSaveResponse saveTodo(AuthUser authUser, TodoSaveRequest todoSaveRequest) {

        User user = User.fromAuthUser(authUser);

        String weather = weatherClient.getTodayWeather();

        Todo newTodo = new Todo(
                todoSaveRequest.getTitle(),
                todoSaveRequest.getContents(),
                weather,
                user
        );
        Todo savedTodo = todoRepository.save(newTodo);

        return new TodoSaveResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getContents(),
                weather,
                new UserResponse(user.getId(), user.getEmail(), user.getNickname())
        );
    }

    @Transactional(readOnly = true)
    public Page<TodoResponse> getTodos(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Todo> todos = todoRepository.findAllByOrderByModifiedAtDesc(pageable);

        return todos.map(todo -> new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(todo.getUser().getId(), todo.getUser().getEmail(), todo.getUser().getNickname()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        ));
    }

    @Transactional(readOnly = true)
    public TodoResponse getTodo(long todoId) {
        Todo todo = todoRepository.findByIdWithUser(todoId)
                .orElseThrow(() -> new InvalidRequestException("Todo not found"));

        User user = todo.getUser();

        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(user.getId(), user.getEmail(), user.getNickname()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<TodoResponse>  getTodoByModifiedAt(TodoFindDateRequest todoFindDateRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Todo> todos = todoRepository.findByOrderByModifiedAtDesc(pageable,todoFindDateRequest.getStartModifiedAt(), todoFindDateRequest.getEndModifiedAt());

        return todos.map(todo -> new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(todo.getUser().getId(), todo.getUser().getEmail(), todo.getUser().getNickname()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        ));
    }

    @Transactional(readOnly = true)
    public Page<TodoResponse> getTodoByWeather(TodoFindWeatherRequest todoFindWeatherRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Todo> todos = todoRepository.findByOrderByWeather(pageable,todoFindWeatherRequest.getWeather());

        return todos.map(todo -> new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(todo.getUser().getId(), todo.getUser().getEmail(), todo.getUser().getNickname()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        ));
    }

    @Transactional(readOnly = true)
    public Page<TodoFindResponse> getTodoByTitle(TodoFindTitleRequest todoFindTitleRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return todoRepository.findByTitle(pageable,todoFindTitleRequest.getTitle());

//        return todos.map(todo -> new TodoFindResponse((long)1,
//                todo.getTitle(),
//                (long)todo.getManagers().size(),(long)todo.getComments().size()
//        ));
    }

    @Transactional(readOnly = true)
    public Page<TodoFindResponse> getTodoByNickname(TodoFindNicknameRequest todoFindNicknameRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return todoRepository.findByNickname(pageable, todoFindNicknameRequest.getNickname());

    }

    @Transactional(readOnly = true)
    public Page<TodoFindResponse> getTodoByDate(TodoFindDateRequest todoFindDateRequest, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return todoRepository.findByCreatedAt(pageable, todoFindDateRequest.getStartModifiedAt(), todoFindDateRequest.getEndModifiedAt());

    }
}
