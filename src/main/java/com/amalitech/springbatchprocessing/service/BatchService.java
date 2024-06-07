package com.amalitech.springbatchprocessing.service;

import com.amalitech.springbatchprocessing.dto.UserDto;
import com.amalitech.springbatchprocessing.entity.UserEntity;
import org.springframework.batch.core.BatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;




public interface BatchService {
    BatchStatus importJsonBulkDatasetToDatabaseJob(MultipartFile multipartFile);
    Page<UserEntity> getAllUsers(Pageable pageable);
    
    BatchStatus importJsonBulkDatasetToDatabaseJobWithoutBatch(MultipartFile file);
    

}
