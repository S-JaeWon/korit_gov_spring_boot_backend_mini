package com.korit.backend_mini.repository;

import com.korit.backend_mini.dto.Response.BoardRespDto;
import com.korit.backend_mini.entity.Board;
import com.korit.backend_mini.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {

    @Autowired
    private BoardMapper boardMapper;

    public int addBoard(Board board) {
        return boardMapper.addBoard(board);
    }

    public List<BoardRespDto> getBoardList() {
        return boardMapper.getBoardList();
    }

    public Optional<BoardRespDto> getBoardByBoardId(Integer boardId) {
        return boardMapper.getBoardByBoardId(boardId);
    }

    public List<BoardRespDto> getBoardByKeyword(String keyword) {
        return boardMapper.getBoardByKeyword(keyword);
    }

    public int modifyBoard(Board board) {
        return boardMapper.modifyBoard(board);
    }

    public int deleteBoard(Integer boardId) {
        return boardMapper.deleteBoard(boardId);
    }

    public List<BoardRespDto> getBoardListByBoardId(Integer boardId) {
        return boardMapper.getBoardListByBoardId(boardId);
    }
}
