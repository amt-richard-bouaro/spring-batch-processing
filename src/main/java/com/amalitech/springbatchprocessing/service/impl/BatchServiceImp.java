package com.amalitech.springbatchprocessing.service.impl;

import com.amalitech.springbatchprocessing.dto.UserDto;
import com.amalitech.springbatchprocessing.entity.UserEntity;
import com.amalitech.springbatchprocessing.enums.UploadStatus;
import com.amalitech.springbatchprocessing.exception.ImportException;
import com.amalitech.springbatchprocessing.repository.UserEntityRepository;
import com.amalitech.springbatchprocessing.service.BatchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class BatchServiceImp implements BatchService {
    
    @Value("${api.upload_directory}")
    private String uploadDirectory;
    
    private static final Logger logger = LoggerFactory.getLogger(BatchServiceImp.class);
    
    
    private final JobLauncher jobLauncher;
    
    private final Job job;
    
    private final UserEntityRepository userRepository;
    
    
    public BatchServiceImp(final JobLauncher jobLauncher, final Job job, UserEntityRepository userRepository) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.userRepository = userRepository;
    }
    
    @Override
    public BatchStatus importJsonBulkDatasetToDatabaseJob(MultipartFile file) {
        
        UploadStatus uploadStatus = this.uploadFile(file);
        
        if(uploadStatus.equals(UploadStatus.ALREADY_EXIST)){
            throw new ImportException("File already exist");
        }
        
        if(uploadStatus.equals(UploadStatus.FAILED)){
            throw new ImportException("File upload failed");
        }
        
       return insertUserDateIntoDatabase();
    }
    
    
    @Override
public BatchStatus importJsonBulkDatasetToDatabaseJobWithoutBatch(MultipartFile file) {
    UploadStatus uploadStatus = this.uploadFile(file);
    
    if (uploadStatus.equals(UploadStatus.ALREADY_EXIST)) {
        throw new ImportException("File already exists");
    }
    
    if (uploadStatus.equals(UploadStatus.FAILED)) {
        throw new ImportException("File upload failed");
    }
    
    ObjectMapper objectMapper = new ObjectMapper();
    try {
        List<UserEntity> data = objectMapper.readValue(file.getInputStream(), new TypeReference<List<UserEntity>>() {});
        persistData(data);
    } catch (IOException e) {
        throw new RuntimeException("Error reading JSON file", e);
    }
    
    return BatchStatus.COMPLETED;
}
    
    @Transactional
    public void persistData(List<UserEntity> data) {
        userRepository.saveAll(data);
    }
    
    
    private BatchStatus insertUserDateIntoDatabase(){
        
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        
        try {
            
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            
            // Get the status of the job execution
            BatchStatus jobStatus = jobExecution.getStatus();
            
            if (jobStatus == BatchStatus.COMPLETED) {
                
                logger.info("Job completed successfully");
                
                return jobStatus;
                
            } else if (jobStatus == BatchStatus.FAILED) {
                
                logger.error("Job failed");
                
                Throwable jobException = jobExecution.getAllFailureExceptions().get(0);
                
                logger.error("Job exception: ", jobException);
                
                throw new ImportException("Job failed", jobException);
                
                
            } else {
                
                logger.warn("Job status: " + jobStatus);
                
                throw new ImportException("Job execution ended with status: " + jobStatus);
            }
           
        } catch (
                JobExecutionAlreadyRunningException | JobParametersInvalidException |
                JobInstanceAlreadyCompleteException | JobRestartException e) {
            
            logger.error(e.getMessage(), e);
            
            throw new ImportException(e.getMessage(), e);
        }
    }
    
    

    private UploadStatus uploadFile(MultipartFile multipartFile) {

//        String fileName = String.format("%s_%s", UUID.randomUUID().toString(), multipartFile.getOriginalFilename()) ;
        
        String fileName = "MOCK_DATA";
        
        File dir = new File(uploadDirectory+fileName);
        
        if(dir.exists()){
            return UploadStatus.ALREADY_EXIST;
        }
        
        Path path = Paths.get(uploadDirectory, fileName);
        
        try{
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return UploadStatus.UPLOADED;
        } catch (Exception e){
         logger.error("Error copying file");
        }
        return UploadStatus.FAILED;
    }
    
    
    @Override
    public Page<UserEntity> getAllUsers(Pageable pageable){
    return userRepository.findAll(pageable);
    }
}
