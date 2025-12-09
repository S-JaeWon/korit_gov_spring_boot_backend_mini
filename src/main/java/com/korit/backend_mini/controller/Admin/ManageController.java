package com.korit.backend_mini.controller.Admin;

import com.korit.backend_mini.security.model.PrincipalUser;
import com.korit.backend_mini.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/manage")
public class ManageController {

    @Autowired
    private ManageService manageService;

    @PostMapping("/userList")
    public ResponseEntity<?> getUserList(@AuthenticationPrincipal PrincipalUser principalUser) {
        return ResponseEntity.ok(manageService.getUserList(principalUser));
    }
}
