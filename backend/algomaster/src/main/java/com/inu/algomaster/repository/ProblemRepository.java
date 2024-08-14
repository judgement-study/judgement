package com.inu.algomaster.repository;

import com.inu.algomaster.data.dto.problem.ProblemDto;
import com.inu.algomaster.data.entity.Problem;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Optional<Problem> findByProblemNumber(Long problemId);

    List<Problem> findAll();

    List<Problem> findByTitleContaining(String keyword);

}
