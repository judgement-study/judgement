package com.inu.algomaster.data.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInResponseDto {

    private String msg;
    private boolean success;
    private String token;

    @Builder
    public SignInResponseDto(String msg, boolean succes, String token){
        this.msg = msg;
        this.success = success;
        this.token = token;
    }
}
