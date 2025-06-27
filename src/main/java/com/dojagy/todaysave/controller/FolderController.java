package com.dojagy.todaysave.controller;

import com.dojagy.todaysave.dto.Result;
import com.dojagy.todaysave.dto.folder.FolderCreateRequestDto;
import com.dojagy.todaysave.dto.folder.FolderResponseDto;
import com.dojagy.todaysave.dto.user.UserPrincipal;
import com.dojagy.todaysave.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/create")
    public Result<FolderResponseDto> createFolder(@RequestBody FolderCreateRequestDto requestDto,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return folderService.createFolder(requestDto, userPrincipal.id());
    }

    @GetMapping("/roots")
    public Result<List<FolderResponseDto>> getRootFolders(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return folderService.rootFolders(userPrincipal.id());
    }

    @GetMapping("/childs/{folderId}")
    public Result<List<FolderResponseDto>> getChildFolders(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                           @PathVariable Long folderId) {
        return folderService.childFolders(userPrincipal.id(), folderId);
    }

    @DeleteMapping("/delete/{folderId}")
    public Result<Void> deleteFolder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                       @PathVariable Long folderId) {
        return folderService.deleteFolder(userPrincipal.id(), folderId);
    }
}
