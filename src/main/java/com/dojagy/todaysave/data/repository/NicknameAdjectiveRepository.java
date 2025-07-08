package com.dojagy.todaysave.data.repository;

import com.dojagy.todaysave.data.entity.NicknameAdjective;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NicknameAdjectiveRepository extends JpaRepository<NicknameAdjective, Long> {
    Page<NicknameAdjective> findAll(Pageable pageable);
}
