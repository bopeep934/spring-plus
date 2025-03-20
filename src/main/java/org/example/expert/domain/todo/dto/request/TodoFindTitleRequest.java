package org.example.expert.domain.todo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TodoFindTitleRequest {

    @NotBlank
    private String title;

}
