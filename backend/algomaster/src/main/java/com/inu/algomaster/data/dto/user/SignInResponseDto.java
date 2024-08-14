package com.inu.algomaster.data.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponseDto {

    private String msg;
    private boolean success;
    private String token;

    @Builder
    public SignInResponseDto(boolean success, String msg, String token){
        this.msg = msg;
        this.success = success;
        this.token = token;
    }
}
