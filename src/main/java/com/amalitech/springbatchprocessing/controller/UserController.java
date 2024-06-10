package com.amalitech.springbatchprocessing.controller;

import com.amalitech.springbatchprocessing.dto.PaginatedUsersResponseDto;
import com.amalitech.springbatchprocessing.dto.SuccessResponseDto;
import com.amalitech.springbatchprocessing.entity.Mappers.UserEntityMapper;
import com.amalitech.springbatchprocessing.entity.UserEntity;
import com.amalitech.springbatchprocessing.enums.ResponseStatus;
import com.amalitech.springbatchprocessing.service.BatchService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.batch.core.BatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@RestController
@RequestMapping("/api/")
@Tag(name = "User Data Bulk Upload")
public class UserController {

  private final BatchService batchService;

  private final UserEntityMapper userEntityMapper;


  public UserController(
      BatchService batchService,
      UserEntityMapper userEntity
  ) {
    this.batchService = batchService;
    this.userEntityMapper = userEntity;
  }

  @PostMapping(value = "v1/users/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(description = "Upload user data.",
      summary = "This endpoint allows uploading bulk data to database. This will be processed without spring batch.",
      responses = {
          @ApiResponse(description = "Success", responseCode = "201"),
          @ApiResponse(description = "Bad Request", responseCode = "400"),
          @ApiResponse(description = "Internal Server Error", responseCode = "500")
      })
  @Timed(value = "import.timer", description = "Time taken to process upload request without spring batch")
  public ResponseEntity<SuccessResponseDto<BatchStatus>> handleImportJsonBulkDatasetToDatabaseJobWithoutBatchProcessor(
      @RequestPart(name = "file") MultipartFile file) {

    BatchStatus batchResponse = batchService.importJsonBulkDatasetToDatabaseJobWithoutBatch(file);

    SuccessResponseDto<BatchStatus> response = new SuccessResponseDto<>(ResponseStatus.SUCCESS,
        "Successfully imported json bulk dataset to database", Instant.now()
        .toString(), batchResponse);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping(value = "v2/users/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(
      description = "Upload user data.",
      summary = "This endpoint allows uploading bulk data to database. This will be proccessed using spring batch.",
      responses = {
          @ApiResponse(description = "Success", responseCode = "201"),
          @ApiResponse(description = "Bad Request", responseCode = "400"),
          @ApiResponse(description = "Internal Server Error", responseCode = "500")})
  public ResponseEntity<SuccessResponseDto<BatchStatus>> handleImportJsonBulkDatasetToDatabaseJob(
      @RequestPart(name = "file") MultipartFile file
  ) {

    BatchStatus batchResponse = batchService.importJsonBulkDatasetToDatabaseJob(file);

    SuccessResponseDto<BatchStatus> response = new SuccessResponseDto<>(ResponseStatus.SUCCESS,
        "Successfully imported json bulk dataset to database", Instant.now()
        .toString(), batchResponse);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping(value = "v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Get saved users ", summary = "This endpoint provides paginated list of users", responses = {
      @ApiResponse(description = "Success", responseCode = "200"),
      @ApiResponse(description = "Internal Server Error", responseCode = "500")})
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
        .users(users.stream()
            .map(userEntityMapper::toDto)
            .toList())
        .build();

    SuccessResponseDto<PaginatedUsersResponseDto> response = new SuccessResponseDto<>(
        ResponseStatus.SUCCESS,
        "Users Retrieved", Instant.now()
        .toString(), paginatedResponse);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }


}
