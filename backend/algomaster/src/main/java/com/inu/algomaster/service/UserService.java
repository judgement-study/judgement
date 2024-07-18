package com.inu.algomaster.service;

import com.inu.algomaster.data.dto.UserRequestDto;
import com.inu.algomaster.data.dto.UserResponseDto;

public interface UserService {

    UserResponseDto getUser(Long userId);
    UserResponseDto saveUser(UserRequestDto userRequestDto);

}
