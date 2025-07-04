package com.dojagy.todaysave.data.repository;

import com.dojagy.todaysave.data.entity.Content;
import com.dojagy.todaysave.data.entity.Folder;
import com.dojagy.todaysave.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentRepository extends JpaRepository<Content, Long> {

    // N+1 문제를 해결하기 위한 Fetch Join 쿼리
    // Content를 조회할 때 연관된 Link와 User도 함께 한방 쿼리로 가져옵니다.
    @Query("SELECT c FROM Content c JOIN FETCH c.link JOIN FETCH c.user WHERE c.folder = :folder")
    Page<Content> findByFolderWithDetails(Folder folder, Pageable pageable);

    // 기본 쿼리 메소드
    Page<Content> findByFolderAndUser(Folder folder, User user, Pageable pageable);
}
