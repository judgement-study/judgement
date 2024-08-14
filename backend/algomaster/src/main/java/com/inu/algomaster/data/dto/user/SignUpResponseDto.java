package com.inu.algomaster.data.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpResponseDto {

    private String msg;
    private boolean success;

    public boolean getSuccess(){
        return success;
    }

    @Builder
    public SignUpResponseDto(String msg, boolean success){
        this.msg = msg;
        this.success = success;
    }
}
