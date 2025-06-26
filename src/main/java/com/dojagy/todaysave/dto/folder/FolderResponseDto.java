package com.dojagy.todaysave.dto.folder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class FolderResponseDto {
    private Long id;
    private String folderName;
    private String color;
    private Long parentId;
    private List<FolderResponseDto> children;
}
