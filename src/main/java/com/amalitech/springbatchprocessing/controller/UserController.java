package com.amalitech.springbatchprocessing.controller;

import com.amalitech.springbatchprocessing.dto.PaginatedUsersResponseDto;
import com.amalitech.springbatchprocessing.dto.SuccessResponseDto;
import com.amalitech.springbatchprocessing.entity.UserEntity;
import com.amalitech.springbatchprocessing.enums.ResponseStatus;
import com.amalitech.springbatchprocessing.service.BatchService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class UserController {
    
    private final BatchService batchService;
    
    
    @PostMapping("v1/users/import")
    public ResponseEntity<SuccessResponseDto<BatchStatus>> handleImportJsonBulkDatasetToDatabaseJobWithoutBatchProcessor(@RequestParam(name = "file") MultipartFile file) {
        
        BatchStatus batchResponse = batchService.importJsonBulkDatasetToDatabaseJobWithoutBatch(file);
        
        SuccessResponseDto<BatchStatus> response = new SuccessResponseDto<>(ResponseStatus.SUCCESS,
                "Successfully imported json bulk dataset to database", Instant.now()
                                                                              .toString(), batchResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("v2/users/import")
    public ResponseEntity<SuccessResponseDto<BatchStatus>> handleImportJsonBulkDatasetToDatabaseJob(
            @RequestParam(name = "file") MultipartFile file
    ) {
        
        BatchStatus batchResponse = batchService.importJsonBulkDatasetToDatabaseJob(file);
        
        SuccessResponseDto<BatchStatus> response = new SuccessResponseDto<>(ResponseStatus.SUCCESS,
                "Successfully imported json bulk dataset to database", Instant.now()
                                                                              .toString(), batchResponse);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "v1/users")
    public ResponseEntity<SuccessResponseDto<PaginatedUsersResponseDto>> handleGettingAllUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "100") int pageSize
    ) {
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        
        Page<UserEntity> users = batchService.getAllUsers(pageable);
        
        PaginatedUsersResponseDto paginatedResponse = PaginatedUsersResponseDto.builder()
                                                                               .pageNumber(users.getNumber())
                                                                               .pageSize(users.getSize())
                                                                               .totalElements(users.getTotalElements())
                                                                               .totalPages(users.getTotalPages())
                                                                               .build();
        
        SuccessResponseDto<PaginatedUsersResponseDto> response = new SuccessResponseDto<>(ResponseStatus.SUCCESS,
                "Users Retrieved", Instant.now()
                                          .toString(), paginatedResponse);
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
}
