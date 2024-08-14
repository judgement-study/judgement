package com.inu.algomaster.data.dto;

import lombok.Builder;

public class SignUpResponseDto {

    private String msg;
    private boolean success;

    @Builder
    public SignUpResponseDto(String msg, boolean success){
        this.msg = msg;
        this.success = success;
    }
}
