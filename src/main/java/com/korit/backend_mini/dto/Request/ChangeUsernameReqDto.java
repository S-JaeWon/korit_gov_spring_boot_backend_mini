package com.korit.backend_mini.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeUsernameReqDto {
    private Integer userId;
    private String username;
}
