package com.korit.backend_mini.controller;

import com.korit.backend_mini.dto.Request.AddBoardReqDto;
import com.korit.backend_mini.dto.Request.DeleteBoardDto;
import com.korit.backend_mini.dto.Request.ModifyBoardReqDto;
import com.korit.backend_mini.security.model.PrincipalUser;
import com.korit.backend_mini.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/add")
    public ResponseEntity<?> addBoard(
            @RequestBody AddBoardReqDto addBoardReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.addBoard(addBoardReqDto, principalUser));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getBoardList() {
        return ResponseEntity.ok(boardService.getBoardList());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardList(@PathVariable Integer boardId) {
        return ResponseEntity.ok(boardService.getBoardByBoardId(boardId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getBoardByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(boardService.getBoardByKeyword(keyword));
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyBoard(
            @RequestBody ModifyBoardReqDto modifyBoardReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.modifyBoard(modifyBoardReqDto, principalUser));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteBoard(
            @RequestBody DeleteBoardDto deleteBoardDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(boardService.deleteBoard(deleteBoardDto , principalUser));
    }
}
