package com.inu.algomaster.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // == 200 == //
    SUCCESS(OK, "OK"),

    // == 4xx == //
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 회원이 존재하지 않습니다."),
    PROBLEM_NOT_FOUND(NOT_FOUND, "해당하는 문제가 존재하지 않습니다"),
    DUPLICATE_MEMBER_ID(CONFLICT, "이미 존재하는 아아디입니다."),
    
    // == 500 == //
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버에서 에러가 발생하였습니다");

    private final HttpStatus status;
    private final String msg;

}
