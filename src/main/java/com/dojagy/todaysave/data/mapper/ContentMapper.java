package com.dojagy.todaysave.data.mapper;

import com.dojagy.todaysave.data.dto.content.ContentResponseDto;
import com.dojagy.todaysave.data.dto.content.LinkResponseDto;
import com.dojagy.todaysave.data.dto.content.TagResponseDto;
import com.dojagy.todaysave.data.entity.Content;
import com.dojagy.todaysave.data.entity.ContentTag;
import com.dojagy.todaysave.data.entity.Link;
import com.dojagy.todaysave.data.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ContentMapper {

    @Mapping(source = "link", target = "link")
    @Mapping(source = "contentTags", target = "tags", qualifiedByName = "contentTagsToTagDtos")
    ContentResponseDto toResponseDto(Content content);

    LinkResponseDto linkToLinkResponseDto(Link link);

    TagResponseDto tagToTagResponseDto(Tag tag);

    @Named("contentTagsToTagDtos")
    default List<TagResponseDto> contentTagsToTagDtos(List<ContentTag> contentTags) {
        if (contentTags == null) {
            return null;
        }
        return contentTags.stream()
                .map(ContentTag::getTag) // ContentTag에서 Tag를 추출
                .map(this::tagToTagResponseDto) // 각 Tag를 TagResponseDto로 변환
                .collect(Collectors.toList());
    }
}
