package com.inu.algomaster.data.dto;

import lombok.Builder;

import java.time.LocalDate;

public class SignInRequestDto {

    private String identifier;
    private String password;

    @Builder
    public SignInRequestDto(String password, String identifier) {
        this.identifier = identifier;
        this.password = password;
    }
}
