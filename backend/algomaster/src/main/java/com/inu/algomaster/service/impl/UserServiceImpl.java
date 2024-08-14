package com.inu.algomaster.service.impl;

import com.inu.algomaster.config.security.TokenGenerator;
import com.inu.algomaster.data.dto.*;
import com.inu.algomaster.data.dto.user.SignInRequestDto;
import com.inu.algomaster.data.dto.user.SignInResponseDto;
import com.inu.algomaster.data.dto.user.SignUpRequestDto;
import com.inu.algomaster.data.dto.user.SignUpResponseDto;
import com.inu.algomaster.data.entity.User;
import com.inu.algomaster.exception.CustomException;
import com.inu.algomaster.repository.UserRepository;
import com.inu.algomaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.inu.algomaster.exception.ErrorCode.MEMBER_NOT_FOUND;
import static java.util.regex.Pattern.matches;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        return null;
    }

    @Override
    // 로그인
    @Transactional
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {

        // id 비교
        String identifier = signInRequestDto.getIdentifier();

        User user = userRepository.findByEmailOrUserId(identifier, identifier)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        // 비밀번호 비교
        if (!matches(signInRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        //로그인 성공
        SignUpResponseDto signUpResponseDto = SignUpResponseDto.builder()
                .success(true)
                .msg("로그인 성공")
                .build();

        //response 데이터 생성
        SignInResponseDto signInResponseDto = new SignInResponseDto(signUpResponseDto.getSuccess(), signUpResponseDto.getMsg(), TokenGenerator.generateToken());

        return signInResponseDto;
    }



    public UserResponseDto getUser(Long uid){

        User user = userRepository.getByUid(uid).orElseThrow(()-> new NoSuchElementException("유저가 존재하지 않습니다.")) ;

        UserResponseDto userResponseDto = UserResponseDto.convertEntityToDto(user);

        return userResponseDto;
    }


    @Override
    public UserResponseDto findUserById(String userId) {
        User user = userRepository.getByUserId(userId).orElseThrow(()-> new NoSuchElementException("유저가 존재하지 않습니다.")) ;

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
