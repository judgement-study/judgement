package com.inu.algomaster.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long uid;

    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String email;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = true, name = "status_message")
    public String statusMessage;

    @Column(nullable = false)
    private String organization;

    @Column(nullable = false)
    private String tier = "unrated"; // 기본 값

    private LocalDate signup_date;

    @Column(nullable = false)
    private String name;

    @Builder
    public User(String userId, String email, String password, String statusMessage, String organization, String name) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.statusMessage = statusMessage;
        this.organization = organization;
        this.tier = "unrated";
        this.signup_date = LocalDate.from(LocalDateTime.now());
        this.name = name;
    }
}
