package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.Request.OAuth2.OAuth2MergeReqDto;
import com.korit.backend_mini.dto.Request.OAuth2.OAuth2SignupReqDto;
import com.korit.backend_mini.dto.Response.ApiRespDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.entity.UserRole;
import com.korit.backend_mini.repository.OAuth2UserRepository;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OAuth2AuthService {

    @Autowired
    private OAuth2UserRepository oAuth2UserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public ApiRespDto<?> signup(OAuth2SignupReqDto oAuth2SignUpReqDto) {
        Optional<User> foundUser = userRepository.getUserByEmail(oAuth2SignUpReqDto.getEmail());

        if (foundUser.isPresent()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("중복된 email 입니다.")
                    .build();
        }

        Optional<User> foundUserByUsername = userRepository.getUserByUsername(oAuth2SignUpReqDto.getUsername());

        if (foundUserByUsername.isPresent()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("중복된 username 입니다.")
                    .build();
        }

        Optional<User> user = userRepository.addUser(oAuth2SignUpReqDto.toUserEntity(bCryptPasswordEncoder));

        if (user.isEmpty()) {
            throw  new RuntimeException("회원 추가 실패");
        }

        UserRole userRole = UserRole.builder()
                .userId(user.get().getUserId())
                .roleId(3)
                .build();

        int userRoleResult = userRoleRepository.addUserRole(userRole);

        if (userRoleResult != 1) {
            throw new RuntimeException("회원 권한 추가 실패");
        }

        int oauth2UserResult = oAuth2UserRepository.addOAuth2User(oAuth2SignUpReqDto.toOAuth2UserEntity(user.get().getUserId()));

        if (oauth2UserResult != 1) {
            throw new RuntimeException("회원 추가 실패");

        }
        return ApiRespDto.builder()
                .status("success")
                .message("회원 가입 완료")
                .data(user.get())
                .build();
    }

    public ApiRespDto<?> merge(OAuth2MergeReqDto oAuth2MergeReqDto) {
        Optional<User> foundUser = userRepository.getUserByEmail(oAuth2MergeReqDto.getEmail());

        if (foundUser.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("사용자 정보를 확인 해주세요.")
                    .build();
        }

        if (!bCryptPasswordEncoder.matches(oAuth2MergeReqDto.getPassword(), foundUser.get().getPassword())) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("사용자 정보를 확인 해주세요.")
                    .build();
        }

        if (!foundUser.get().isActive()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("탈퇴 처리된 계정입니다.")
                    .build();
        }

        int result = oAuth2UserRepository.addOAuth2User(oAuth2MergeReqDto.toEntity(foundUser.get().getUserId()));

        if (result != 1) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("회원 연동 중 문제 발생")
                    .build();
        }

        return ApiRespDto.builder()
                .status("success")
                .message("회원 연동 성공")
                .build();
    }
}
