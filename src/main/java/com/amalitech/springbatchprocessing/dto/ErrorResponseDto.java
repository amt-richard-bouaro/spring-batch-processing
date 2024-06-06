package com.amalitech.springbatchprocessing.dto;


import com.amalitech.springbatchprocessing.enums.ResponseStatus;

public record ErrorResponseDto<T>(ResponseStatus status, String message, String timestamp, T error) {
}
