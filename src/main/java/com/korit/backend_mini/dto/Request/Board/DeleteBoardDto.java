package com.korit.backend_mini.dto.Request.Board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBoardDto {
    private Integer userId;
    private Integer boardId;
}
