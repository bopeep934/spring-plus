package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoFindResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TodoRepositoryQuery {
    Optional<Todo> findByIdWithUser(Long todoId);

    Page<TodoFindResponse> findByTitle(Pageable pageable, String title);

    Page<TodoFindResponse> findByCreatedAt(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    Page<TodoFindResponse> findByNickname(Pageable pageable, String Nickname);

}
