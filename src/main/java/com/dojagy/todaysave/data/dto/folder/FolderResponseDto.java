package com.dojagy.todaysave.data.dto.folder;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class FolderResponseDto {
    private Long id;
    private String folderName;
    private String color;
    private Long parentId;
    private Long ownerId;
    private List<FolderResponseDto> children;
    private LocalDateTime lastContentDate;
    private List<FolderShareResponseDto> shares; // 공유 정보 목록 (추가)
}
