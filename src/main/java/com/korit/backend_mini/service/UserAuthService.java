package com.korit.backend_mini.service;

import com.korit.backend_mini.dto.Request.SigninReqDto;
import com.korit.backend_mini.dto.Request.SignupReqDto;
import com.korit.backend_mini.dto.Response.ApiRespDto;
import com.korit.backend_mini.entity.User;
import com.korit.backend_mini.entity.UserRole;
import com.korit.backend_mini.repository.UserRepository;
import com.korit.backend_mini.repository.UserRoleRepository;
import com.korit.backend_mini.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Transactional(rollbackFor = Exception.class) // 트랜잭션, 어떤 예외라도 롤백
    public ApiRespDto<?> signup(SignupReqDto signupReqDto) {
        Optional<User> foundUser = userRepository.getUserByEmail(signupReqDto.getEmail());

        if (foundUser.isPresent()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("중복된 email 입니다.")
                    .build();
        }

        Optional<User> foundUserByUsername = userRepository.getUserByUsername(signupReqDto.getUsername());

        if (foundUserByUsername.isPresent()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("중복된 username 입니다.")
                    .build();
        }

        Optional<User> user = userRepository.addUser(signupReqDto.toEntity(bCryptPasswordEncoder));

        if (user.isEmpty()) {
            throw  new RuntimeException("회원 추가 실패");
        }

        UserRole userRole = UserRole.builder()
                .userId(user.get().getUserId())
                .roleId(3)
                .build();

        int result = userRoleRepository.addUserRole(userRole);
        if (result != 1) {
            throw new RuntimeException("회원 권한 추가 실패");
        }

        return ApiRespDto.builder()
                .status("success")
                .message("회원 가입 완료")
                .data(user.get())
                .build();
    }

    public ApiRespDto<?> signin(SigninReqDto signinReqDto) {
        Optional<User> foundUser = userRepository.getUserByEmail(signinReqDto.getEmail());

        if (foundUser.isEmpty()) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("사용자 정보를 확인 해주세요.")
                    .build();
        }

        if (!bCryptPasswordEncoder.matches(signinReqDto.getPassword(), foundUser.get().getPassword())) {
            return ApiRespDto.builder()
                    .status("failed")
                    .message("사용자 정보를 확인 해주세요.")
                    .build();
        }

        String accessToken = jwtUtils.generateAccessToken(foundUser.get().getUserId().toString());

        return ApiRespDto.builder()
                .status("success")
                .message("로그인 성공")
                .data(accessToken)
                .build();
    }
}
