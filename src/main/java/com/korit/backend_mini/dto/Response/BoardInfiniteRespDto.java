package com.korit.backend_mini.dto.Response;

import com.korit.backend_mini.dto.Request.Board.BoardNextCursor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardInfiniteRespDto {
    private List<BoardRespDto> boardRespDtoList;
    private boolean hasNext;
    private BoardNextCursor boardNextCursor;
}
