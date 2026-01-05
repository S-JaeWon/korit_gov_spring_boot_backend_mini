package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.Response.ApiRespDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManageService {

    @Autowired
    private UserRepository userRepository;

    public ApiRespDto<?> getUserList(PrincipalUser principalUser) {
        if (principalUser.getUserRoles().stream().noneMatch(userRole -> userRole.getRoleId() == 1)) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("접근 권한이 없습니다.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("user 전체 조회 완료")
                .data(userRepository.getUserList())
                .build();
    }

    public ApiRespDto<?> getUserByUsername(String username, PrincipalUser principalUser) {
        if (principalUser.getUserRoles().stream().noneMatch(userRole -> userRole.getRoleId() == 1)) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("접근 권한이 없습니다.")
                    .build();
        }

        Optional<User> foundUser = userRepository.getUserByUsername(username);

        if (foundUser.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("해당 유저가 존재 하지 않습니다.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("회원정보 조회 완료")
                .data(foundUser.get())
                .build();
    }
}
