package com.dojagy.todaysave.data.mapper;

import com.dojagy.todaysave.data.dto.user.UserResponseDto;
import com.dojagy.todaysave.data.dto.user.UserSignUpRequestDto;
import com.dojagy.todaysave.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDt", ignore = true)
    User toEntity(UserSignUpRequestDto dto);

    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "grade", target = "grade")
    UserResponseDto toResponseDto(User user);

}
