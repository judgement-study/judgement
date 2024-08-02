package com.inu.algomaster.service.impl;

import com.inu.algomaster.data.dto.UserRequestDto;
import com.inu.algomaster.data.dto.UserResponseDto;
import com.inu.algomaster.data.entity.User;
import com.inu.algomaster.repository.UserRepository;
import com.inu.algomaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserResponseDto getUser(Long uid){

        User user = userRepository.getByUid(uid).orElseThrow(()-> new NoSuchElementException("유저가 존재하지 않습니다.")) ;

        UserResponseDto userResponseDto = UserResponseDto.convertEntityToDto(user);

        return userResponseDto;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto){

        User user = UserRequestDto.convertDtoToEntity(userRequestDto);
        userRepository.save(user);

        UserResponseDto userResponseDto = UserResponseDto.convertEntityToDto(user);

        return userResponseDto;
    }

}
