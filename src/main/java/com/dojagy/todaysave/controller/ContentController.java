package com.dojagy.todaysave.controller;

import com.dojagy.todaysave.dto.Result;
import com.dojagy.todaysave.dto.content.ContentCreateRequestDto;
import com.dojagy.todaysave.dto.content.ContentResponseDto;
import com.dojagy.todaysave.dto.content.UrlMetadataDto;
import com.dojagy.todaysave.dto.user.UserPrincipal;
import com.dojagy.todaysave.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping
    public Result<ContentResponseDto> create(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestBody ContentCreateRequestDto requestDto) {
        return contentService.createContent(userPrincipal.id(), requestDto);
    }

    @GetMapping("/metadata")
    public Result<UrlMetadataDto> metadata(@RequestParam String url) {
        return contentService.urlMetadata(url);
    }
}
