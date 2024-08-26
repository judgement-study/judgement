package com.inu.algomaster.controller;


import com.inu.algomaster.data.dto.UserRequestDto;
import com.inu.algomaster.data.dto.UserResponseDto;
import com.inu.algomaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<UserResponseDto> getUser(Long uid){

        UserResponseDto userResponseDto = userService.getUser(uid);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> createUser (@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = userService.saveUser(userRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }
}
