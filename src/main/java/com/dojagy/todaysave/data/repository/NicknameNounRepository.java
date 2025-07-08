package com.dojagy.todaysave.data.repository;

import com.dojagy.todaysave.data.entity.NicknameNoun;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NicknameNounRepository extends JpaRepository<NicknameNoun, Long> {
    Page<NicknameNoun> findAll(Pageable pageable);
}
