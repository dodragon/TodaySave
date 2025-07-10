package com.dojagy.todaysave.data.repository;

import com.dojagy.todaysave.data.entity.Folder;
import com.dojagy.todaysave.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    Page<Folder> findByOwnerIdAndParentIsNull(Long userId, Pageable pageable);
    Page<Folder> findByOwnerIdAndParent(Long userId, Folder parent, Pageable pageable);
    Optional<Folder> findByIdAndOwner(Long id, User user);
}
