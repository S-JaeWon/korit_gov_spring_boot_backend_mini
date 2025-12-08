package com.korit.backend_mini.dto.Request;

import com.korit.backend_mini.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyBoardReqDto {
    private Integer boardId;
    private String title;
    private String content;
    private Integer userId;

    public Board toEntity() {
        return Board.builder()
                .boardId(boardId)
                .title(title)
                .content(content)
                .build();
    }
}
