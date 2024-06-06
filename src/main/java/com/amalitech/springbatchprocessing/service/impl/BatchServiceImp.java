package com.amalitech.springbatchprocessing.service.impl;

import com.amalitech.springbatchprocessing.exception.ImportException;
import com.amalitech.springbatchprocessing.service.BatchService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BatchServiceImp implements BatchService {
    
    private static final Logger logger = LoggerFactory.getLogger(BatchServiceImp.class);
    
    
    private final JobLauncher jobLauncher;
    
    private final Job job;
    
    @Override
    public void importJsonBulkDatasetToDatabaseJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
                 JobInstanceAlreadyCompleteException | JobRestartException e) {
            e.printStackTrace();
            
//            logger.error(e.getMessage());
            
            throw new ImportException(e.getMessage());
        }
    }
}
