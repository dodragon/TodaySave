package com.dojagy.todaysave.data.repository;

import com.dojagy.todaysave.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndSnsKey(String email, String snsKey);
    Optional<User> findByEmailOrSnsKey(String email, String snsKey);
    Optional<User> findBySnsKey(String snsKey);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
}