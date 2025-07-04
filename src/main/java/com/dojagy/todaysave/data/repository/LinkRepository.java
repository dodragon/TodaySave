package com.dojagy.todaysave.data.repository;

import com.dojagy.todaysave.data.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    // 캐노니컬 링크 문자열로 Link 엔티티를 조회
    Optional<Link> findByCanonicalLink(String canonicalLink);
}
