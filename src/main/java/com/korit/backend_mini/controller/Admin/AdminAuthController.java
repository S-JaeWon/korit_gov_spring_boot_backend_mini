package com.korit.backend_mini.controller.Admin;

import com.korit.backend_mini.dto.Request.Auth.SigninReqDto;
import com.korit.backend_mini.dto.Request.Auth.SignupReqDto;
import com.korit.backend_mini.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {

    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        return ResponseEntity.ok(adminAuthService.signup(signupReqDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signup(@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok(adminAuthService.signin(signinReqDto));
    }
}
