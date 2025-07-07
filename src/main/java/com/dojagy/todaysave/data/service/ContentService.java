package com.dojagy.todaysave.data.service;

import com.dojagy.todaysave.data.dto.Result;
import com.dojagy.todaysave.data.dto.content.ContentCreateRequestDto;
import com.dojagy.todaysave.data.dto.content.ContentResponseDto;
import com.dojagy.todaysave.data.dto.content.UrlMetadataDto;
import com.dojagy.todaysave.data.entity.*;
import com.dojagy.todaysave.data.repository.*;
import com.dojagy.todaysave.data.mapper.ContentMapper;
import com.dojagy.todaysave.util.UrlMetadataExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final FolderRepository folderRepository;
    private final CategoryRepository categoryRepository;
    private final LinkRepository linkRepository;
    private final TagRepository tagRepository;
    private final ContentMapper contentMapper;
    private final UrlMetadataExtractor extractor;
    private final UserRepository userRepository;

    @Transactional
    public Result<ContentResponseDto> createContent(Long userId, ContentCreateRequestDto requestDto) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Result.FAILURE("사용자를 찾을 수 없습니다.");
        }

        Folder folder = folderRepository.findById(requestDto.getFolderId()).orElse(null);
        if (folder == null) {
            return Result.FAILURE("폴더를 찾을 수 없습니다.");
        }

        Category category = categoryRepository.findById(requestDto.getCategoryId()).orElse(null);

        Link link = linkRepository.findByCanonicalLink(requestDto.getCanonicalUrl()).orElseGet(() ->
                Link.builder()
                        .url(requestDto.getCanonicalUrl())
                        .title(requestDto.getTitle())
                        .description(requestDto.getDescription())
                        .thumbnailUrl(requestDto.getThumbnailUrl())
                        .build()
        );

        Content newContent = Content.builder()
                .sharedLink(requestDto.getSharedUrl())
                .memo(requestDto.getMemo())
                .user(user)
                .folder(folder)
                .category(category)
                .link(link)
                .build();

        if (requestDto.getTags() != null) {
            for (String tagName : requestDto.getTags()) {
                Tag tag = tagRepository.findByName(tagName).orElseGet(() ->
                        tagRepository.save(
                                Tag.builder()
                                        .name(tagName)
                                        .build()
                        )
                );
                newContent.addTag(tag);
            }
        }

        Content savedContent = contentRepository.save(newContent);

        return Result.SUCCESS("컨텐츠 저장을 성공했습니다.", contentMapper.toResponseDto(savedContent));
    }

    @Transactional
    public Result<UrlMetadataDto> urlMetadata(String url) {
        UrlMetadataDto dto = extractor.extract(url).orElse(null);

        if (dto == null) {
            return Result.FAILURE("저장 할 수 없는 링크 입니다. 다른 링크를 저장해 주세요.");
        }

        return Result.SUCCESS("URL 메타데이터 조회 완료했습니다.", dto);
    }

    @Transactional
    public Result<Page<ContentResponseDto>> contents(Long folderId, Pageable pageable) {
        Folder folder = folderRepository.findById(folderId).orElse(null);
        if (folder == null) {
            return Result.FAILURE("폴더를 찾을 수 없습니다.");
        }

        return Result.SUCCESS("폴더 컨텐츠 목록 조회 성공했습니다.", contentRepository.findByFolderWithDetails(folder, pageable)
                .map(contentMapper::toResponseDto));
    }
}
