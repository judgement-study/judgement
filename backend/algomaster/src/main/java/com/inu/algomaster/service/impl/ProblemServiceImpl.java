package com.inu.algomaster.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inu.algomaster.data.dto.problem.ApiResponse;
import com.inu.algomaster.data.dto.problem.ProblemReqDto;
import com.inu.algomaster.data.dto.problem.ProblemResDto;
import com.inu.algomaster.data.entity.Problem;
import com.inu.algomaster.exception.CustomException;
import com.inu.algomaster.repository.ProblemRepository;
import com.inu.algomaster.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.inu.algomaster.exception.ErrorCode.PROBLEM_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;

    @Override
    public Long save(Long userId, Long problemId) {
        return null;
    }


//    @Override
//    public Long save(Long userId, Long problemId) {
//
//                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
//
//        return null;
//    }

    @Transactional
    public Problem findById(Long problemId) {

        return problemRepository.findByProblemNumber(problemId)
                .orElseThrow(() -> new CustomException(PROBLEM_NOT_FOUND));
    }

    @Transactional
    public Long delete(Long saveProblemId) {
        return null;
    }

    @Override
    public String defaultTest() {
        RestTemplate restTemplate = new RestTemplate();
        String analysisServerUrl = "http://117.16.243.158:9010/";
        String res = restTemplate.getForObject(analysisServerUrl, String.class);

        return res;
    }

    @Transactional
    public List<Problem> findAllProblems() {

        return problemRepository.findAll();
    }

    @Override
    public ProblemResDto searchProblemByProblemNumber(Long problemNumber) {

        Optional<Problem> findProblem = problemRepository.findByProblemNumber(problemNumber);

        ProblemResDto analysisProblemDto = null;

        if(findProblem.isEmpty()){
            WebClient webClient = WebClient.create();
            ProblemReqDto request = ProblemReqDto.builder()
                    .problemNumber(problemNumber)
                    .build();

//          RestTemplate restTemplate = new RestTemplate();
            String analysisServerUrl = "http://192.168.123.3:9010/prob_generate";

            //ApiResponse response = restTemplate.getForObject(analysisServerUrl, ApiResponse.class);
            ApiResponse response = webClient.post()
                    .uri(analysisServerUrl)
                    .body(Mono.just(request), ProblemReqDto.class)
                    .retrieve()
                    .bodyToMono(ApiResponse.class)
                    .block();

            log.info("json value : {}", response.getOutput());

            if(!"success".equals(response.getStatus())){
                throw new RuntimeException("Problem not found in analysis server");
            }

            analysisProblemDto = ProblemResDto.builder()
                    .problemNumber(problemNumber)
                    .title(response.getOutput().title())
                    .problemDescription(response.getOutput().problemDescription())
                    .outputDescription(response.getOutput().outputDescription())
                    .inputDescription(response.getOutput().inputDescription())
                    .timeLimit(response.getOutput().timeLimit())
                    .memoryLimit(response.getOutput().memoryLimit())
                    .accuracy(response.getOutput().accuracy())
                    .build();

            if (analysisProblemDto != null) {

                //분석 서버로부터 받은 데이터를 데이터베이스에 저장
                saveProblem(analysisProblemDto);

            } else {
                throw new RuntimeException("Problem not found in analysis server");
            }
        }
        else{
            analysisProblemDto = ProblemResDto.toDto(findProblem);
        }

        return analysisProblemDto;
    }

    @Override
    public ProblemResDto saveProblem(ProblemResDto problemResDto) {
        Problem problem = ProblemResDto.toEntity(problemResDto);
        problemRepository.save(problem);

        return problemResDto;
    }

    @Override
    public List<Problem> searchProblems(String query, String category) {
        return problemRepository.findAll().stream()
                .filter(problem -> problem.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
//                .filter(problem -> problem.getTitle().toLowerCase().contains(query.toLowerCase())
//                ; //&&
//                        //problem.getCategory().toLowerCase().contains(category.toLowerCase()))
//                //.collect(Collectors.toList());
    }
}

