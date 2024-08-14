package com.inu.algomaster.data.dto.problem;

import lombok.Builder;

@Builder
public record ImageDataDto(
        String url,
        String precedingSentence
) {
}

