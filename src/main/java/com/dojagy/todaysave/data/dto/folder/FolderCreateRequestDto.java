package com.dojagy.todaysave.data.dto.folder;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FolderCreateRequestDto {
    @NotBlank(message = "폴더 이름은 필수입니다.")
    private String folderName;
    @NotBlank(message = "폴더 색상은 필수입니다.")
    private String color;
    private Long parentId;
}
