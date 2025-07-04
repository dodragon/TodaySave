package com.dojagy.todaysave.dto.folder;

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
    private List<FolderResponseDto> children;
    private LocalDateTime lastContentDate;
}
