package com.dojagy.todaysave.data.repository;

import com.dojagy.todaysave.data.entity.Folder;
import com.dojagy.todaysave.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Page<Folder> findByUserIdAndParentIsNull(Long userId, Pageable pageable);
    Page<Folder> findByUserIdAndParent(Long userId, Folder parent, Pageable pageable);
    Optional<Folder> findByIdAndUser(Long id, User user);
}
