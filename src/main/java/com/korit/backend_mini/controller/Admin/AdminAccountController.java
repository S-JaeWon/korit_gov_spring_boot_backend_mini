package com.korit.backend_mini.controller.Admin;

import com.korit.backend_mini.dto.Request.Account.ChangePwdReqDto;
import com.korit.backend_mini.dto.Request.Account.ChangeUsernameReqDto;
import com.korit.backend_mini.security.model.PrincipalUser;
import com.korit.backend_mini.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/account")
public class AdminAccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePwdReqDto changePwdReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(accountService.changePassword(changePwdReqDto, principalUser));
    }

    @PostMapping("/change/username")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangeUsernameReqDto changeUsernameReqDto,
            @AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(accountService.changeUsername(changeUsernameReqDto, principalUser));
    }


}
