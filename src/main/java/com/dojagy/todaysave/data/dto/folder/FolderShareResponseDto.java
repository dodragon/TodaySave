package com.dojagy.todaysave.data.dto.folder;

import com.dojagy.todaysave.data.entity.value.SharePermissionLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FolderShareResponseDto {
    private Long userId; // 공유받은 사용자 ID
    private String userNickname; // 공유받은 사용자 닉네임 (UI 표시에 유용)
    private SharePermissionLevel permissionLevel; // 권한
    private LocalDateTime sharedDt; // 공유 시작일
}
