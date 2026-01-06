package com.korit.backend_mini.dto.Request.Board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BoardNextCursor {
    private LocalDateTime cursorCreateDt;
    private Integer cursorBoardId;
}
