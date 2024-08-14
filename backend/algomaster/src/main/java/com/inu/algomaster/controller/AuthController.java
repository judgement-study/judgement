package com.inu.algomaster.controller;


import com.inu.algomaster.data.dto.user.SignInRequestDto;
import com.inu.algomaster.data.dto.user.SignInResponseDto;
import com.inu.algomaster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public SignInResponseDto signIn (@RequestBody SignInRequestDto signInRequestDto){
        SignInResponseDto signInResponseDto = userService.signIn(signInRequestDto);

        return signInResponseDto;
    }

}
