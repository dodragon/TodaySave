package com.dojagy.todaysave.repository;

import com.dojagy.todaysave.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByUserIdAndParentIsNull(Long userId);
}
