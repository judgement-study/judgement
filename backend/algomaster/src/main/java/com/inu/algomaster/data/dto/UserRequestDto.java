package com.inu.algomaster.data.dto;


import com.inu.algomaster.data.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    private String userId;
    private String email;
    private String password;
    private String statusMessage;
    private String organization;
    private String name;

    @Builder
    public UserRequestDto(String userId, String email, String password, String statusMessage, String organization, String tier, LocalDate signupDate, String name) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.statusMessage = statusMessage;
        this.organization = organization;
        this.name = name;
    }



    public static User convertDtoToEntity(UserRequestDto UserRequestDto){

        return User.builder()
                .email(UserRequestDto.getEmail())
                .name(UserRequestDto.getName())
                .userId(UserRequestDto.getUserId())
                .password(UserRequestDto.getPassword())
                .statusMessage(UserRequestDto.getStatusMessage())
                .organization(UserRequestDto.getOrganization())
                .build();
    }
}
