package com.dojagy.todaysave.data.repository;

import com.dojagy.todaysave.data.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
    Page<Category> findByNameContaining(String keyword, Pageable pageable);
}
