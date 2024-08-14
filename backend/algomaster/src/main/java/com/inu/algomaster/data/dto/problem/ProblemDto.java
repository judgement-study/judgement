package com.inu.algomaster.data.dto.problem;

import com.inu.algomaster.data.entity.Problem;
import com.inu.algomaster.data.entity.User;
import lombok.Getter;

@Getter
public class ProblemDto {

    private Long problemNumber;
    private User user;
    private String problemTitle;
    private String timeLimit;
    private String memoryLimit;

    private String problemContent;
    private String inputFormat;
    private String outputDescription;
    private String accuracy;
//    private String sampleInputs;
//    private String sampleOutputs;

    public ProblemDto(Problem problem){
        this.problemNumber = problem.getProblemNumber();
        //this.user = problem.getUser();
        this.problemTitle = problem.getTitle();
        this.timeLimit = problem.getTimeLimit();
        this.memoryLimit = problem.getMemoryLimit();

        this.problemContent = problem.getProblemDescription();
        this.inputFormat = problem.getInputDescription();
        this.outputDescription = problem.getOutputDescription();
        this.accuracy = problem.getAccuracy();
//        this.sampleInputs = problem.getSampleInputs();
//        this.sampleOutputs = problem.getSampleOutputs();
    }
}
