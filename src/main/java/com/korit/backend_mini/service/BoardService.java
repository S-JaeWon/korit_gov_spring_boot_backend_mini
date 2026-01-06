package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.Request.Board.*;
import com.korit.backend_mini.dto.Response.ApiRespDto;
import com.korit.backend_mini.dto.Response.BoardInfiniteRespDto;
import com.korit.backend_mini.dto.Response.BoardRespDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.BoardRepository;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    public ApiRespDto<?> addBoard(AddBoardReqDto addBoardReqDto, PrincipalUser principalUser) {
        if (!addBoardReqDto.getUserId().equals(principalUser.getUserId())) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("잘못된 접근입니다.")
                    .build();
        }

        Optional<User> foundUserByUserId= userRepository.getUserByUserId(addBoardReqDto.getUserId());

        if (foundUserByUserId.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("존재하지 않은 user 입니다.")
                    .build();
        }

        int result = boardRepository.addBoard(addBoardReqDto.toEntity());

        if (result != 1) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("게시물 추가에 실패 했습니다. 다시 시도 해주세요.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("게시물 추가 완료")
                .data(addBoardReqDto)
                .build();
    }

    public ApiRespDto<?> getBoardList() {

        List<BoardRespDto> boardList = boardRepository.getBoardList();

        return ApiRespDto.builder()
                .status("success")
                .message("게시물 전체 조회 완료")
                .data(boardList)
                .build();
    }

    public ApiRespDto<?> getBoardInfinite(Integer limit, LocalDateTime cursorCreateDt, Integer cursorBoardId) {
        int limitPlusOne = limit + 1;

        List<BoardRespDto> boardRespDtoList = boardRepository.getBoardInfinite(cursorCreateDt, cursorBoardId, limitPlusOne);

        boolean hasNext = boardRespDtoList.size() > limit;

        if (hasNext) {
            boardRespDtoList = boardRespDtoList.subList(0, limit);
        }

        BoardNextCursor boardNextCursor = null;
        if (!boardRespDtoList.isEmpty() && hasNext) {
            BoardRespDto last = boardRespDtoList.get(boardRespDtoList.size() - 1);
            boardNextCursor = new BoardNextCursor(last.getCreateDt(), last.getBoardId());

        }

        BoardInfiniteRespDto boardInfiniteRespDto = new BoardInfiniteRespDto(boardRespDtoList, hasNext, boardNextCursor);

        return  ApiRespDto.builder()
                .status("success")
                .message("조회 완료")
                .data(boardInfiniteRespDto)
                .build();
    }

    public ApiRespDto<?> getBoardByBoardId(Integer boardId) {

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(boardId);

        if (foundBoard.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("해당 게시물이 존재하지 않습니다.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("게시물 단건 조회 완료")
                .data(foundBoard.get())
                .build();
    }

    public ApiRespDto<?> getBoardByKeyword(String keyword) {

        List<BoardRespDto> foundBoard = boardRepository.getBoardByKeyword(keyword);

        return ApiRespDto.builder()
                .status("success")
                .message("게시물 검색 조회 완료")
                .data(foundBoard)
                .build();
    }

    public ApiRespDto<?> modifyBoard(ModifyBoardReqDto modifyBoardReqDto, PrincipalUser principalUser) {
        if (!modifyBoardReqDto.getUserId().equals(principalUser.getUserId())) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("잘못된 접근입니다.")
                    .build();
        }

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(modifyBoardReqDto.getBoardId());

        if (foundBoard.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("존재 하지 않는 게시물 입니다.")
                    .build();
        }

        int result = boardRepository.modifyBoard(modifyBoardReqDto.toEntity());

        if (result != 1) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("게시물 수정 중 오류 발생. 다시 시도해주세요.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("게시물 수정 완료")
                .data(modifyBoardReqDto)
                .build();
    }

    public ApiRespDto<?> deleteBoard(DeleteBoardDto deleteBoardDto, PrincipalUser principalUser) {
        if (!deleteBoardDto.getUserId().equals(principalUser.getUserId())
                && principalUser.getUserRoles().stream()
                .noneMatch(userRole -> userRole.getRole().getRoleId() == 1)) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("잘못된 접근입니다.")
                    .build();
        }

        Optional<BoardRespDto> foundBoard = boardRepository.getBoardByBoardId(deleteBoardDto.getBoardId());

        if (foundBoard.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("해당 게시물이 존재 하지 않습니다.")
                    .build();
        }

        int result = boardRepository.deleteBoard(deleteBoardDto.getBoardId());

        if (result != 1) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("게시물 삭제 중 오류 발생. 다시 시도해주세요.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("게시물 삭제 완료")
                .build();
    }

    public ApiRespDto<?> getBoardListByUserId(Integer userId) {
        Optional<User> foundUser = userRepository.getUserByUserId(userId);

        if (foundUser.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("존재하지 않는 user 입니다.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("게시물 조회 완료")
                .data(boardRepository.getBoardListByUserId(userId))
                .build();
    }
}
