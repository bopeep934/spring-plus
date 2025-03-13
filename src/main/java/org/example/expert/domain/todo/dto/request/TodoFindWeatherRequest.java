package org.example.expert.domain.todo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TodoFindWeatherRequest {
    @NotBlank
    private String weather;
}
