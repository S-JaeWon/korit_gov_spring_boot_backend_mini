package com.korit.backend_mini.controller.User;

import com.korit.backend_mini.dto.Request.Auth.SigninReqDto;
import com.korit.backend_mini.dto.Request.Auth.SignupReqDto;
import com.korit.backend_mini.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/auth")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        return ResponseEntity.ok(userAuthService.signup(signupReqDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signup(@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok(userAuthService.signin(signinReqDto));
    }
}
