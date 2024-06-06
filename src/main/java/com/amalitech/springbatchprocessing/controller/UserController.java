package com.amalitech.springbatchprocessing.controller;

import com.amalitech.springbatchprocessing.dto.SuccessResponseDto;
import com.amalitech.springbatchprocessing.enums.ResponseStatus;
import com.amalitech.springbatchprocessing.service.BatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.type.NullType;
import java.time.Instant;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class UserController {
    
    private final BatchService batchService;
    
    @GetMapping("v1/users/import")
    public ResponseEntity<SuccessResponseDto<NullType>> handleImportJsonBulkDatasetToDatabaseJob() {
        
      batchService.importJsonBulkDatasetToDatabaseJob();
        
        SuccessResponseDto<NullType>  response = new SuccessResponseDto<>(ResponseStatus.SUCCESS, "Successfully imported json bulk dataset to database",
                Instant.now().toString(), null
                );
      
      return new ResponseEntity<> (response, HttpStatus.CREATED);
    }

}
