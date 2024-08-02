package com.inu.algomaster.data.dto;


import com.inu.algomaster.data.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserResponseDto {

    private String userId;
    private String email;
    private String password;
    private String statusMessage;
    private String organization;
    private String tier;
    private LocalDate signUpDate;
    private String name;

    @Builder
    public UserResponseDto(String userId, String email, String password, String statusMessage, String organization, String tier, LocalDate signupDate, String name) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.statusMessage = statusMessage;
        this.organization = organization;
        this.tier = tier;
        this.signUpDate = signupDate;
        this.name = name;
    }

    public static UserResponseDtoBuilder builder(){return new UserResponseDtoBuilder();}


    public static UserResponseDto convertEntityToDto(User user){
        return UserResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userId(user.getUserId())
                .password(user.getPassword())
                .organization(user.getOrganization())
                .statusMessage(user.getStatusMessage())
                .tier(user.getTier())
                .signupDate(user.getSignup_date())
                .name(user.getName())
                .build();
    }
}
