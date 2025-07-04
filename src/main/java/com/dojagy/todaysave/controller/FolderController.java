package com.dojagy.todaysave.controller;

import com.dojagy.todaysave.data.dto.Result;
import com.dojagy.todaysave.data.dto.folder.FolderCreateRequestDto;
import com.dojagy.todaysave.data.dto.folder.FolderResponseDto;
import com.dojagy.todaysave.data.dto.user.UserPrincipal;
import com.dojagy.todaysave.data.service.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping("")
    public Result<FolderResponseDto> createFolder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                  @Valid @RequestBody FolderCreateRequestDto requestDto) {
        return folderService.createFolder(requestDto, userPrincipal.id());
    }

    @GetMapping("")
    public Result<Page<FolderResponseDto>> rootFolders(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                       @PageableDefault(size = 20, sort = "createDt", direction = Sort.Direction.DESC) Pageable pageable) {
        return folderService.rootFolders(userPrincipal.id(), pageable);
    }

    @GetMapping("/{folderId}/children")
    public Result<Page<FolderResponseDto>> childFolders(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                        @PathVariable Long folderId,
                                                        @PageableDefault(size = 20, sort = "createDt", direction = Sort.Direction.DESC) Pageable pageable) {
        return folderService.childFolders(userPrincipal.id(), folderId, pageable);
    }

    @DeleteMapping("/{folderId}")
    public Result<Void> deleteFolder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                       @PathVariable Long folderId) {
        return folderService.deleteFolder(userPrincipal.id(), folderId);
    }

    @PatchMapping("/{folderId}")
    public Result<FolderResponseDto> updateFolder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                  @PathVariable Long folderId,
                                                  @Valid @RequestBody FolderCreateRequestDto requestDto) {
        return folderService.updateFolder(userPrincipal.id(), folderId, requestDto);
    }
}
