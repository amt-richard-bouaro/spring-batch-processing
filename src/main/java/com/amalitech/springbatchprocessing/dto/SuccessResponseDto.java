package com.amalitech.springbatchprocessing.dto;

import com.amalitech.springbatchprocessing.enums.ResponseStatus;

public record SuccessResponseDto<T>(ResponseStatus status, String message, String timestamp, T data) {
}
