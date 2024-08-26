package com.inu.algomaster.repository;

import com.inu.algomaster.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByUid(Long uid);

    Optional<User> getByUserId(String userId);

    Optional<User> findByEmailOrUserId(String email, String userId);

    Optional<User> findByUserId(String userId);

    Optional<User> getByEmail(String email);


    boolean existsByUid(Long uid);

}
