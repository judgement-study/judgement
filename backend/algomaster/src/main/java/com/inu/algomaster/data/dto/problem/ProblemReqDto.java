package com.inu.algomaster.data.dto.problem;

import lombok.Builder;

@Builder
public record ProblemReqDto(
        Long problemNumber
) {
}
