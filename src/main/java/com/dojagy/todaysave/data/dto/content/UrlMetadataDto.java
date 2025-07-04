package com.dojagy.todaysave.data.dto.content;

import com.dojagy.todaysave.data.entity.value.LinkType;
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
    private LinkType linkType;
}
