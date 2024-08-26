package com.inu.algomaster.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid") // pk로 지정
    private Long uid;

    @Column(name = "user_id", updatable = false, nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public void setRoles(List<String> role){
        this.roles = role;
    }

    @Builder
    protected User(String userId, String email, String password, String statusMessage, String organization, String name) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.statusMessage = statusMessage;
        this.organization = organization;
        this.tier = "unrated";
        this.signup_date = LocalDate.from(LocalDateTime.now());
        this.name = name;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}