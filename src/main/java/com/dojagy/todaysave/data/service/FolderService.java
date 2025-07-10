package com.dojagy.todaysave.data.service;

import com.dojagy.todaysave.data.dto.Result;
import com.dojagy.todaysave.data.dto.folder.FolderCreateRequestDto;
import com.dojagy.todaysave.data.dto.folder.FolderResponseDto;
import com.dojagy.todaysave.data.entity.Folder;
import com.dojagy.todaysave.data.entity.User;
import com.dojagy.todaysave.data.mapper.FolderMapper;
import com.dojagy.todaysave.data.repository.FolderRepository;
import com.dojagy.todaysave.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.BiFunction;

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

            if(!parent.getOwner().getId().equals(userId)) {
                return Result.FAILURE("상위 폴더에 대한 접근 권한이 없습니다.");
            }

            folder.setParent(parent);
        }

        Folder saveFolder = folderRepository.save(folder);

        return Result.SUCCESS("폴더 생성이 완료되었습니다.", folderMapper.toResponseDto(saveFolder));
    }

    @Transactional
    public Result<Page<FolderResponseDto>> rootFolders(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            return Result.FAILURE("사용자를 찾을 수 없습니다.");
        }

        Page<Folder> rootFolders = folderRepository.findByOwnerIdAndParentIsNull(userId, pageable);

        return Result.SUCCESS("폴더 리스트 조회에 성공했습니다.", rootFolders.map(folderMapper::toResponseDto));
    }

    @Transactional
    public Result<Page<FolderResponseDto>> childFolders(Long userId, Long folderId, Pageable pageable) {
        return checkUserAndFolder(userId, folderId, (user, folder) -> {
            Page<Folder> pageFolder = folderRepository.findByOwnerIdAndParent(userId, folder, pageable);
            return Result.SUCCESS("하위 폴더 조회에 성공하였습니다.", pageFolder.map(folderMapper::toResponseDto));
        });
    }

    @Transactional
    public Result<Void> deleteFolder(Long userId, Long folderId) {
        return checkUserAndFolder(userId, folderId, (user, folder) -> {
            folderRepository.delete(folder);
            return Result.SUCCESS(folder.getFolderName() + "폴더를 삭제 완료했습니다.");
        });
    }

    @Transactional
    public Result<FolderResponseDto> updateFolder(Long userId, Long folderId, FolderCreateRequestDto requestDto) {
        return checkUserAndFolder(userId, folderId, (user, folder) -> {
            folder.setFolderName(requestDto.getFolderName());
            folder.setColor(requestDto.getColor());

            if(requestDto.getParentId() != null) {
                Folder parent = folderRepository.findById(requestDto.getParentId()).orElse(null);
                folder.setParent(parent);
            }

            return Result.SUCCESS("폴더를 수정했습니다.", folderMapper.toResponseDto(folder));
        });
    }

    private <T> Result<T> checkUserAndFolder(Long userId, Long folderId, BiFunction<User, Folder, Result<T>> operation) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            return Result.FAILURE("사용자를 찾을 수 없습니다.");
        }

        Folder folder = folderRepository.findByIdAndOwner(folderId, user).orElse(null);
        if(folder == null) {
            return Result.FAILURE("폴더를 찾을 수 없습니다.");
        }

        return operation.apply(user, folder);
    }
}
