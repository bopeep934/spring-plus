package org.example.expert.domain.todo.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.QTodoFindResponse;
import org.example.expert.domain.todo.dto.response.TodoFindResponse;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;
    QTodo todos = QTodo.todo;
    QManager managers = QManager.manager;
    QUser users = QUser.user;
    QComment comments = QComment.comment;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {

        return Optional.ofNullable(jpaQueryFactory
                .select(todos)
                .from(todos)
                .leftJoin(todos.user, user)
                .where(todos.id.eq(todoId))
                .fetchOne());
    }

    @Override
    public Page<TodoFindResponse> findByTitle(Pageable pageable, String title) {
        List<TodoFindResponse> todoPage = jpaQueryFactory
                .select(new QTodoFindResponse(todos.id, todos.title, managers.count(), comments.count()))
                .from(todos)
                .leftJoin(todos.managers, managers)
                .leftJoin(todos.comments, comments)
                .where(todos.title.like("%" + title + "%"))
                .groupBy(todos.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(todos.count())
                .from(todos)
                .where(todos.title.like(title))
                .fetchOne();

        return new PageImpl<>(todoPage, pageable, count);
    }

    @Override
    public Page<TodoFindResponse> findByCreatedAt(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {
        List<TodoFindResponse> todoList = jpaQueryFactory
                .select(new QTodoFindResponse(todos.id, todos.title, managers.count(), comments.count()))
                .from(todos)
                .leftJoin(todos.managers, managers)
                .leftJoin(todos.comments, comments)
                .where(todos.createdAt.between(startDate, endDate))
                .groupBy(todos.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(todos.count())
                .from(todos)
                .where(todos.createdAt.between(startDate, endDate))
                .fetchOne();

        return new PageImpl<>(todoList, pageable, count);
    }

    @Override
    public Page<TodoFindResponse> findByNickname(Pageable pageable, String nickname) {

        List<TodoFindResponse> todoList = jpaQueryFactory
                .select(new QTodoFindResponse(todos.id, todos.title, managers.count(), comments.count()))
                .from(todos)
                .leftJoin(todos.managers, managers)
                .leftJoin(todos.comments, comments)
                .leftJoin(managers.user, users)
                .where(users.nickname.contains(nickname))
                .groupBy(todos.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory
                .select(todos.count())
                .from(todos)
                .leftJoin(todos.managers, managers)
                .leftJoin(managers.user, users)
                .where(users.nickname.contains(nickname))
                .fetchOne();

        return new PageImpl<>(todoList, pageable, count);

    }

}
