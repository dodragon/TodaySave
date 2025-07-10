package com.dojagy.todaysave.data.mapper;

import com.dojagy.todaysave.data.dto.folder.FolderCreateRequestDto;
import com.dojagy.todaysave.data.dto.folder.FolderResponseDto;
import com.dojagy.todaysave.data.dto.folder.FolderShareResponseDto;
import com.dojagy.todaysave.data.entity.Folder;
import com.dojagy.todaysave.data.entity.FolderShare;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FolderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    // 2. user -> owner 로 변경
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "contents", ignore = true)
    // 3. 새로 추가된 필드들도 ignore 처리
    @Mapping(target = "shares", ignore = true)
    @Mapping(target = "lastContentDate", ignore = true)
    Folder toEntity(FolderCreateRequestDto folder);

    @Mapping(source = "parent.id", target = "parentId")
    // 4. owner.id를 ownerId로 매핑
    @Mapping(source = "owner.id", target = "ownerId")
        // 5. shares는 uses에 등록된 FolderShareMapper가 자동으로 처리
    FolderResponseDto toResponseDto(Folder folder);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.nickname", target = "userNickname")
    FolderShareResponseDto toResponseDto(FolderShare folderShare);
}
