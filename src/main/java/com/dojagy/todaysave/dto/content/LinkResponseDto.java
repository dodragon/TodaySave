package com.dojagy.todaysave.dto.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkResponseDto {
    private String canonicalLink;
    private String title;
    private String description;
    private String thumbnailUrl;
}
