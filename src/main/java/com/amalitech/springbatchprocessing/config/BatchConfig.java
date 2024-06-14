package com.amalitech.springbatchprocessing.config;

import com.amalitech.springbatchprocessing.entity.UserEntity;
import com.amalitech.springbatchprocessing.repository.UserEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

  private final UserEntityRepository userEntityRepository;

  private final JobRepository jobRepository;

  private final PlatformTransactionManager platformTransactionManager;

  @Bean
  public JsonItemReader<UserEntity> jsonItemReader(ObjectMapper objectMapper) {
    JacksonJsonObjectReader<UserEntity> jsonObjectReader = new JacksonJsonObjectReader<>(
        UserEntity.class);
    jsonObjectReader.setMapper(objectMapper);

    return new JsonItemReader<>(new FileSystemResource("src/main/resources/MOCK_DATA.json"),
        jsonObjectReader);
  }

  @Bean
  public UserProcessor processor() {
    return new UserProcessor();
  }

  @Bean
  public RepositoryItemWriter<UserEntity> writer() {
    RepositoryItemWriter<UserEntity> writer = new RepositoryItemWriter<>();

    writer.setRepository(userEntityRepository);

    writer.setMethodName("save");

    return writer;
  }


  public Step importStep() {
    ObjectMapper objectMapper = new ObjectMapper();

    // support Java 8 date time apis
    objectMapper.registerModule(new JavaTimeModule());

    return new StepBuilder("jsonImport", jobRepository)
        .<UserEntity, UserEntity>chunk(100, platformTransactionManager)
        .reader(jsonItemReader(objectMapper))
        .processor(processor())
        .writer(writer())
//        .taskExecutor(taskExecutor())
        .build();
  }

  @Bean
  public Job job() {
    return new JobBuilder("jsonImportJob", jobRepository)
        .start(importStep())
        .build();
  }


  @Bean
  TimedAspect timedAspect(MeterRegistry registry) {
    return new TimedAspect(registry);
  }


  public TaskExecutor taskExecutor() {
    SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();

    asyncTaskExecutor.setConcurrencyLimit(30);

    return asyncTaskExecutor;
  }

}
