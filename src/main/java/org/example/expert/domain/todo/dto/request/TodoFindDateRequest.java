package org.example.expert.domain.todo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class TodoFindDateRequest {
    @NotBlank
    private LocalDateTime startModifiedAt;
    @NotBlank
    private LocalDateTime endModifiedAt;
}
