package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TodoFindResponse {

    private final Long id;
    private final String title;
    private final Long managerNum;
    private final Long commentNum;

    @QueryProjection
    public TodoFindResponse(Long id, String title, Long managerNum, Long commentNum) {
        this.id = id;
        this.title = title;
        this.managerNum = managerNum;
        this.commentNum = commentNum;
    }
}
