package com.amalitech.springbatchprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class PaginatedUsersResponseDto {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private List<UserDto> users = new ArrayList<>();
    
}