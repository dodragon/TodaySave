package com.dojagy.todaysave.dto.content;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UrlMetadataDto {
    private String url;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String faviconUrl;
    private String canonicalUrl;
}
