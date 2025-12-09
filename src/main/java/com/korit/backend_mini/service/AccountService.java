package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.Request.Account.ChangePwdReqDto;
import com.korit.backend_mini.dto.Request.Account.ChangeUsernameReqDto;
import com.korit.backend_mini.dto.Response.ApiRespDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.security.model.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApiRespDto<?> changePassword(
            ChangePwdReqDto changePwdReqDto,
            PrincipalUser principalUser
    ) {

        if (!changePwdReqDto.getUserId().equals(principalUser.getUserId())) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("잘못된 접근입니다.")
                    .build();
        }

        Optional<User> foundUser = userRepository.getUserByUserId(changePwdReqDto.getUserId());
        if (foundUser.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("존재하지 않은 사용자입니다.")
                    .build();
        }

        User user = foundUser.get();

        if (bCryptPasswordEncoder.matches(changePwdReqDto.getOldPassword(), user.getPassword())) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("현재 비밀번호가 일치 하지 않습니다.")
                    .build();
        }

        if (bCryptPasswordEncoder.matches(changePwdReqDto.getNewPassword(), user.getPassword())) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("이미 사용중인 비밀번호 입니다.")
                    .build();
        }

        user.setPassword(bCryptPasswordEncoder.encode(changePwdReqDto.getNewPassword()));

        int result = userRepository.changePassword(user);

        if (result != 1) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("비밀번호 변경에 실패했습니다. 다시 시도해주세요.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("비밀번호 변경 완료, 다시 로그인 해주세요.")
                .build();
    }

    public ApiRespDto<?> changeUsername(
            ChangeUsernameReqDto changeUsernameReqDto,
            PrincipalUser principalUser
    ) {
        if (!changeUsernameReqDto.getUserId().equals(principalUser.getUserId())) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("잘못된 접근 입니다.")
                    .build();
        }

        Optional<User> foundUser = userRepository.getUserByUserId(changeUsernameReqDto.getUserId());
        if (foundUser.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("존재하지 않은 사용자 입니다.")
                    .build();
        }

        Optional<User> foundUsername = userRepository.getUserByUsername(changeUsernameReqDto.getUsername());

        if (foundUsername.isPresent()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("이미 사용중인 username 입니다.")
                    .build();
        }

        User user = foundUser.get();

        user.setUsername(changeUsernameReqDto.getUsername());

        int result = userRepository.changeUsername(user);

        if (result != 1) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("비밀번호 변경에 실패했습니다. 다시 시도해주세요.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("username 변경 완료")
                .build();
    }

    public ApiRespDto<?> withdraw(PrincipalUser principalUser) {
        Optional<User> foundUser = userRepository.getUserByUserId(principalUser.getUserId());
        if (foundUser.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("존재하지 않은 user 입니다.")
                    .build();
        }

        User user = foundUser.get();

        if (!user.isActive()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("이미 탈퇴처리된 계정입니다.")
                    .build();
        }

        int result = userRepository.withdraw(user.getUserId());
        if (result != 1) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("탈퇴 처리 실패, 다시 시도해주세요.")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("탈퇴신청 완료")
                .build();
    }
}
