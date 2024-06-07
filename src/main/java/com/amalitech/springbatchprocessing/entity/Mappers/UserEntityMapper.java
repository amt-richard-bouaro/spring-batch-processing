package com.amalitech.springbatchprocessing.entity.Mappers;

import com.amalitech.springbatchprocessing.dto.UserDto;
import com.amalitech.springbatchprocessing.entity.UserEntity;

public interface UserEntityMapper {
    UserEntity toEntity(UserDto userDto);
    
    UserDto toDto(UserEntity userEntity);
}
