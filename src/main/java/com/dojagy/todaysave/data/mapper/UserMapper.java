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
    // User 엔티티에 새로 추가된 컬렉션 필드들은 ignore 처리
    @Mapping(target = "ownedFolders", ignore = true)
    @Mapping(target = "folderShares", ignore = true)
    User toEntity(UserSignUpRequestDto dto);

    // UserResponseDto는 폴더 목록을 포함하지 않으므로 수정할 필요 없음
    // ReportingPolicy.IGNORE 정책에 따라 ownedFolders, folderShares 필드는 무시됨
    UserResponseDto toResponseDto(User user);

}
