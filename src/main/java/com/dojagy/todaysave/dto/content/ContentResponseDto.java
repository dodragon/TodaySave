package com.dojagy.todaysave.dto.content;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ContentResponseDto {
    private Long id;
    private String memo;
    private String sharedLink;
    private LocalDateTime createDt;
    private LinkResponseDto link;
    private List<TagResponseDto> tags;
}
