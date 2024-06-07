package com.amalitech.springbatchprocessing.entity.Mappers;


import com.amalitech.springbatchprocessing.dto.UserDto;
import com.amalitech.springbatchprocessing.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapperImp implements UserEntityMapper {
    
    private final ModelMapper modelMapper;
    
    public UserEntityMapperImp(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    
    @Override
    public UserEntity toEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
    
    @Override
    public UserDto toDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }
    
}
