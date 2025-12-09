package com.korit.backend_mini.dto.Request.Account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePwdReqDto {
    private Integer userId;
    private String oldPassword;
    private String newPassword;
}
