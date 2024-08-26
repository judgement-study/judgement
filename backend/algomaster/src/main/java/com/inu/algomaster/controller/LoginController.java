//package com.inu.algomaster.controller;
//
//import com.inu.algomaster.data.dto.user.SignInRequestDto;
//import com.inu.algomaster.data.dto.user.SignInResponseDto;
//import com.inu.algomaster.service.impl.ProblemServiceImpl;
//import com.inu.algomaster.service.impl.UserServiceImpl;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/login")
//@RequiredArgsConstructor
//@Slf4j
//public class LoginController {
//
//    private final UserServiceImpl userService;
//
//    @GetMapping("/sign-in")
//    public SignInResponseDto signIn (@RequestBody SignInRequestDto signInRequestDto){
//        SignInResponseDto signInResponseDto = userService.signIn(signInRequestDto);
//
//        return signInResponseDto;
//    }
//}
//~