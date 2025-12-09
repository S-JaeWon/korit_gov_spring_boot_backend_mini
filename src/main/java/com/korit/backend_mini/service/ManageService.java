package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.Response.ApiRespDto;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ManageService {

    @Autowired
    private UserRepository userRepository;

    public ApiRespDto<?> getUserList(PrincipalUser principalUser) {
        if (principalUser.getUserRoles().stream().noneMatch(userRole -> userRole.getUserRoleId() == 1)) {
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
}
