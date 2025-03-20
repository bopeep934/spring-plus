package org.example.expert.domain.common.log;


import jakarta.persistence.*;
import lombok.Getter;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.todo.entity.Todo;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long managerId;

    private String action;

    private String message;

    private LocalDateTime createdAt;

    public Log(Long managerId, String action, String message) {
        this.managerId = managerId;
        this.action = action;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public Log() {

    }
}
