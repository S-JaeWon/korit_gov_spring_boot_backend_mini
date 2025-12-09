package com.korit.backend_mini.dto.Request.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninReqDto {
    private String email;
    private String password;
}
