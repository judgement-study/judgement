package com.inu.algomaster.controller;

import com.inu.algomaster.data.dto.problem.ProblemDto;
import com.inu.algomaster.data.dto.problem.ProblemResDto;
import com.inu.algomaster.data.entity.Problem;
import com.inu.algomaster.service.impl.ProblemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/problem")
@RequiredArgsConstructor
@Slf4j
public class ProblemController {


    private final ProblemServiceImpl problemService;


    //모든 문제 검색
    @GetMapping("/problemset")
    public List<ProblemDto> findAll() {
        List<Problem> problems = problemService.findAllProblems();
        return problems.stream()
                .map(ProblemDto::new)
                .collect(Collectors.toList());
    }

    //번호로 문제 검색
    @GetMapping("/{problemNumber}")
    public ResponseEntity<ProblemResDto> findByProblemNumber(@PathVariable("problemNumber") Long problemNumber) {

        ProblemResDto problemResDto = problemService.searchProblemByProblemNumber(problemNumber);
        return ResponseEntity.status(HttpStatus.OK).body(problemResDto);
        //return problemService.defaultTest();
    }

}
