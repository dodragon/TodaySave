package com.dojagy.todaysave.service;

import com.dojagy.todaysave.dto.Result;
import com.dojagy.todaysave.dto.folder.FolderCreateRequestDto;
import com.dojagy.todaysave.dto.folder.FolderResponseDto;
import com.dojagy.todaysave.entity.Folder;
import com.dojagy.todaysave.entity.User;
import com.dojagy.todaysave.mapper.FolderMapper;
import com.dojagy.todaysave.repository.FolderRepository;
import com.dojagy.todaysave.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final FolderMapper folderMapper;

    @Transactional
    public Result<FolderResponseDto> createFolder(FolderCreateRequestDto requestDto, Long userId) {
        Folder folder = folderMapper.toEntity(requestDto);

        User currentUser = userRepository.findById(userId).orElse(null);
        if(currentUser == null) {
            return Result.FAILURE("사용자를 찾을 수 없습니다.");
        }

        if(requestDto.getParentId() != null) {
            Folder parent = folderRepository.findById(requestDto.getParentId()).orElse(null);
            if(parent == null) {
                return Result.FAILURE("상위 폴더를 찾을 수 없습니다.");
            }

            if(!parent.getUser().getId().equals(userId)) {
                return Result.FAILURE("상위 폴더에 대한 접근 권한이 없습니다.");
            }

            folder.setParent(parent);
        }

        Folder saveFolder = folderRepository.save(folder);

        return Result.SUCCESS("폴더 생성이 완료되었습니다.", folderMapper.toResponseDto(saveFolder));
    }

    @Transactional
    public Result<List<FolderResponseDto>> rootFolders(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            return Result.FAILURE("사용자를 찾을 수 없습니다.");
        }

        List<Folder> rootFolders = folderRepository.findByUserIdAndParentIsNull(userId);

        return Result.SUCCESS("폴더 리스트 조회에 성공했습니다.", rootFolders.stream()
                .map(folderMapper::toResponseDto)
                .collect(Collectors.toList()));
    }

    @Transactional
    public Result<List<FolderResponseDto>> childFolders(Long userId, Long folderId) {
        return checkUserAndFolder(userId, folderId, (user, folder) ->
                Result.SUCCESS("하위 폴더 조회에 성공하였습니다.", folder.getChildren().stream()
                .map(folderMapper::toResponseDto)
                .collect(Collectors.toList())));
    }

    @Transactional
    public Result<Void> deleteFolder(Long userId, Long folderId) {
        return checkUserAndFolder(userId, folderId, (user, folder) -> {
            folderRepository.delete(folder);
            return Result.SUCCESS(folder.getFolderName() + "폴더를 삭제 완료했습니다.");
        });
    }

    private <T> Result<T> checkUserAndFolder(Long userId, Long folderId, BiFunction<User, Folder, Result<T>> operation) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            return Result.FAILURE("사용자를 찾을 수 없습니다.");
        }

        Folder folder = folderRepository.findByIdAndUser(folderId, user).orElse(null);
        if(folder == null) {
            return Result.FAILURE("폴더를 찾을 수 없습니다.");
        }

        return operation.apply(user, folder);
    }
}
