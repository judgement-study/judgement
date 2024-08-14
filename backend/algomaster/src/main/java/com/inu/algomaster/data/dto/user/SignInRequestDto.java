package com.inu.algomaster.data.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignInRequestDto {

    private String identifier;
    private String password;

    @Builder
    public SignInRequestDto(String password, String identifier) {
        this.identifier = identifier;
        this.password = password;
    }
}
