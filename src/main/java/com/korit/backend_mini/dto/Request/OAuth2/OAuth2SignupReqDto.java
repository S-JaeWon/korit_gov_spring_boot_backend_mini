package com.korit.backend_mini.dto.Request.OAuth2;

import com.korit.backend_mini.entity.OAuth2User;
import com.korit.backend_mini.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2SignupReqDto {
    private String email;
    private String password;
    private String username;
    private String provider;
    private String providerUserId;

    public User toUserEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .username(username)
                .build();
    }

    public OAuth2User toOAuth2UserEntity(int userId) {
        return OAuth2User.builder()
                .userId(userId)
                .provider(provider)
                .providerUserId(providerUserId)
                .build();
    }
}
