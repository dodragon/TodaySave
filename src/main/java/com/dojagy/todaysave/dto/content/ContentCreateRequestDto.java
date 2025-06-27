package com.dojagy.todaysave.dto.content;

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
    private List<String> tags;
}
