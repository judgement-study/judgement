package com.inu.algomaster.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "problems")
public class Problem {

    @Id
    @Column(name = "problem_number")
    private Long problemNumber;

    //@ManyToOne
    //@JoinColumn(name = "user_id", nullable = false)

//    private User user;

    @Column(nullable = false)
    private String title;

//    @Column(nullable = false)
//    private String description;
//
//    @Column(nullable = false)
//    private Long level;

    private LocalDate createdAt;

    @Column(nullable = true)
    private String imageDataDtoList;

    private String timeLimit;

    private String memoryLimit;

    @Column(columnDefinition = "TEXT")
    private String problemDescription;  // prob_description in output

    @Column(columnDefinition = "TEXT")
    private String inputDescription;  // input_description in output

    @Column(columnDefinition = "TEXT")
    private String outputDescription;  // output_description in output

    private String accuracy;  // accuracy in output

//    @Column(columnDefinition = "TEXT")
//    private String sampleInputs;  // sample_inputs in output

//    @Column(columnDefinition = "TEXT")
//    private String sampleOutputs;  // sample_outputs in output

    @Builder
    public Problem(User user, String title, Long problemNumber, String timeLimit, String memoryLimit, String accuracy,
                   String problemDescription, String inputDescription, String outputDescription,
                   String sampleInputs, String sampleOutputs, String status, String imageDataDtoList) {
        //this.user = user;
        this.title = title;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.accuracy = accuracy;
        this.problemDescription = problemDescription;
        this.inputDescription = inputDescription;
        this.outputDescription = outputDescription;
//        this.sampleInputs = sampleInputs;
//        this.sampleOutputs = sampleOutputs;
        this.imageDataDtoList = imageDataDtoList;
        this.problemNumber = problemNumber;
        this.createdAt = LocalDate.from(LocalDateTime.now());
    }
}
