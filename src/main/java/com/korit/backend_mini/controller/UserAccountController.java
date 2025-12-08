package com.korit.backend_mini.controller;

import com.korit.backend_mini.dto.Request.ChangePwdReqDto;
import com.korit.backend_mini.dto.Request.ChangeUsernameReqDto;
import com.korit.backend_mini.security.model.PrincipalUser;
import com.korit.backend_mini.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/account")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePwdReqDto changePwdReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changePassword(changePwdReqDto, principalUser));
    }

    @PostMapping("/change/username")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangeUsernameReqDto changeUsernameReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(userAccountService.changeUsername(changeUsernameReqDto, principalUser));
    }

}
