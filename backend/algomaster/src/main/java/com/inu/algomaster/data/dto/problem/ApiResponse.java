package com.inu.algomaster.data.dto.problem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {

    private String status;
    private ProblemResDto output;

}
