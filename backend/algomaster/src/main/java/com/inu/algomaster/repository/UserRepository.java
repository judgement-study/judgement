package com.inu.algomaster.repository;

import com.inu.algomaster.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getByUid(Long uid);

    boolean existsByUid(Long uid);

}
