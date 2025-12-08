package com.korit.backend_mini.mapper;

import com.korit.backend_mini.dto.Response.BoardRespDto;
import com.korit.backend_mini.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    Optional<BoardRespDto> getBoardByBoardId(Integer boardId);
    List<BoardRespDto> getBoardByKeyword(String keyword);
    List<BoardRespDto> getBoardList();
    int addBoard(Board board);
    int modifyBoard(Board board);
    int deleteBoard(Integer boardId);
}
