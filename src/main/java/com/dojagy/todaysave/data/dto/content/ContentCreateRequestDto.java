package com.dojagy.todaysave.data.dto.content;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContentCreateRequestDto {
    private String title;
    private String description;
    private String sharedUrl;
    private String canonicalUrl;
    private String memo;
    private String thumbnailUrl;
    private Long folderId;
    private Long categoryId;
    private List<String> tags;
}
