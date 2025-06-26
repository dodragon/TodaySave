package com.dojagy.todaysave.mapper;

import com.dojagy.todaysave.dto.folder.FolderCreateRequestDto;
import com.dojagy.todaysave.dto.folder.FolderResponseDto;
import com.dojagy.todaysave.entity.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FolderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "children", ignore = true)
    Folder toEntity(FolderCreateRequestDto folder);

    @Mapping(source = "parent_id", target = "parentId")
    FolderResponseDto toResponseDto(Folder folder);
}
