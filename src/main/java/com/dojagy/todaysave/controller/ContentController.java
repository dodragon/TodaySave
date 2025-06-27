package com.dojagy.todaysave.controller;

import com.dojagy.todaysave.dto.Result;
import com.dojagy.todaysave.dto.content.ContentCreateRequestDto;
import com.dojagy.todaysave.dto.content.ContentResponseDto;
import com.dojagy.todaysave.dto.content.UrlMetadataDto;
import com.dojagy.todaysave.dto.user.UserPrincipal;
import com.dojagy.todaysave.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping("")
    public Result<ContentResponseDto> create(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestBody ContentCreateRequestDto requestDto) {
        return contentService.createContent(userPrincipal.id(), requestDto);
    }

    @GetMapping("/{folderId}")
    public Result<Page<ContentResponseDto>> contents(@PathVariable Long folderId,
                                                     @PageableDefault(size = 20, sort = "createDt", direction = Sort.Direction.DESC) Pageable pageable) {
        return contentService.contents(folderId, pageable);
    }

    @GetMapping("/metadata")
    public Result<UrlMetadataDto> metadata(@RequestParam String url) {
        return contentService.urlMetadata(url);
    }
}
