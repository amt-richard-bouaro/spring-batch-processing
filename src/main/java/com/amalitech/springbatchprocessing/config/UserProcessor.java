package com.amalitech.springbatchprocessing.config;

import com.amalitech.springbatchprocessing.entity.UserEntity;
import org.springframework.batch.item.ItemProcessor;

public class UserProcessor implements ItemProcessor<UserEntity, UserEntity> {
    
    @Override
    public UserEntity process(UserEntity user) throws Exception {
        return user;
    }
}
