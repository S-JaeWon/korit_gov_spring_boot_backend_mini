package com.korit.backend_mini.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiRespDto<T> {
    private String status;
    private String message;
    private T data;
}
