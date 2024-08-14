//package com.inu.algomaster.controller;
//
//
//import com.inu.algomaster.data.entity.Problem;
//import com.inu.algomaster.service.ProblemService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/search")
//@Slf4j
//public class SearchController {
//
//    private final ProblemService problemService;
//
//    @Autowired
//    public SearchController(ProblemService problemService) {
//        this.problemService = problemService;
//    }
//
//    @GetMapping("/search")
//    public List<Problem> search(
//            @RequestParam(value = "q", required = false, defaultValue = "") String query,
//            @RequestParam(value = "c", required = false, defaultValue = "") String category
//    ) {
//        return problemService.searchProblems(query, category);
//    }
//}
