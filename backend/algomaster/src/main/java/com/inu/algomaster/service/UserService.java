package com.inu.algomaster.service;

import com.inu.algomaster.data.dto.*;
import com.inu.algomaster.data.dto.user.SignInRequestDto;
import com.inu.algomaster.data.dto.user.SignInResponseDto;
import com.inu.algomaster.data.dto.user.SignUpRequestDto;
import com.inu.algomaster.data.dto.user.SignUpResponseDto;

public interface UserService {

    UserResponseDto getUser(Long userId);

    UserResponseDto findUserById(String userId);

    UserResponseDto saveUser(UserRequestDto userRequestDto);

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto);
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto);

}
