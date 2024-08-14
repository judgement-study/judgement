package com.inu.algomaster.data.dto.problem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inu.algomaster.data.entity.Problem;
import lombok.Builder;

import java.util.Optional;

@Builder
public record ProblemResDto(

        String title,

        @JsonProperty("problem_number")
        Long problemNumber,
        @JsonProperty("time_limit")
        String timeLimit,
        @JsonProperty("memory_limit")
        String memoryLimit,
        String accuracy,
        @JsonProperty("prob_description")
        String problemDescription,
        @JsonProperty("input_description")
        String inputDescription,
        @JsonProperty("output_description")
        String outputDescription
//        @JsonProperty("sample_inputs")
//        String sampleInputs,
//
//        @JsonProperty("sample_outputs")
//        String sampleOutputs,
//        List<ImageDataDto> imageDataDtoList

//        @JsonProperty("image_data_dto_list")
//        String imageDataDtoList

) {
    public static Problem toEntity(ProblemResDto problemResDto){

        return Problem.builder()
                .title(problemResDto.title())
                .memoryLimit(problemResDto.memoryLimit())
                .timeLimit(problemResDto.timeLimit())
                .accuracy(problemResDto.accuracy())
                .problemDescription(problemResDto.problemDescription())
                .inputDescription(problemResDto.inputDescription())
                .outputDescription(problemResDto.outputDescription())

//                .sampleInputs(problemResDto.sampleInputs())
//                .sampleOutputs(problemResDto.sampleOutputs())
//                .imageDataDtoList("이미지")
                .problemNumber(problemResDto.problemNumber())
                .build();
    }

    public static ProblemResDto toDto(Optional<Problem> problemOpt) {

        if (problemOpt.isPresent()) {
            Problem problem = problemOpt.get();
            return ProblemResDto.builder()
                    .title(problem.getTitle())
                    .problemNumber(problem.getProblemNumber())
                    .timeLimit(problem.getTimeLimit())
                    .memoryLimit(problem.getMemoryLimit())
                    .accuracy(problem.getAccuracy())
                    .problemDescription(problem.getProblemDescription())  // Fixed method name
                    .inputDescription(problem.getInputDescription())
                    .outputDescription(problem.getOutputDescription())
//                    .sampleInputs(problem.getSampleInputs())
//                    .sampleOutputs(problem.getSampleOutputs())
                    // Assume imageDataDtoList is handled separately or converted as needed
                    .build();
        } else {
            // Handle the case when the problem is not present (e.g., throw an exception)
            throw new RuntimeException("Problem not found");
        }
    }
}

