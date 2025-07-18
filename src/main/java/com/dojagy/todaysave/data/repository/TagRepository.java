package com.dojagy.todaysave.data.repository;

import com.dojagy.todaysave.data.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    // 태그 이름으로 Tag 엔티티를 조회
    Optional<Tag> findByName(String name);
}
