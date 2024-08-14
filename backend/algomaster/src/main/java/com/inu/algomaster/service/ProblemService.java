package com.inu.algomaster.service;

import com.inu.algomaster.data.dto.UserRequestDto;
import com.inu.algomaster.data.dto.UserResponseDto;
import com.inu.algomaster.data.dto.problem.ProblemResDto;
import com.inu.algomaster.data.entity.Problem;

import java.util.List;
import java.util.Optional;

public interface ProblemService {

    Long save(Long userId, Long problemId);
    Problem findById(Long problemId);
    Long delete(Long saveProblemId);

    List<Problem> findAllProblems();

    ProblemResDto saveProblem(ProblemResDto problemResDto);

    List<Problem> searchProblems(String query, String category);

    ProblemResDto searchProblemByProblemNumber(Long problemNumber);

    String defaultTest();

}
