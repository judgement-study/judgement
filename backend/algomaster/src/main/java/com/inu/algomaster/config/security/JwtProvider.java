package com.inu.algomaster.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.access-token-valid-time}")
    private Long accessTokenValidTime;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long userId, String loginId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("userId", userId);
        claims.put("type", "access");
        claims.put("roles", roles);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }

    public Long getUserId(String token) {
        Long userId = Long.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userId").toString());
        log.info("[getUserId] 토큰 기반 회원 구별 정보 추출 완료, userId : {}", userId);
        return userId;
    }

    // 필터에서 인증 성공 후, SecurityContextHolder 에 저장할 Authentication 을 생성
    //UsernamePasswordAuthenticationToken 클래스를 사용
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails loginId : {}", userDetails.getUsername());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰 클래스를 사용하기 위해 초기화를 위한 UserDetails가 필요하다.
    //Jwts.parser()를 통해 secretKey를 설정하고 클레임을 추출해서 토큰을 생성할 때 넣었던 sub값을 추출합니다.
    public String getUsername(String token) {
        String loginId = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, loginId : {}", loginId);
        return loginId;
    }

    public String getAuthorizationToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try {
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("Bearer ")) {
                throw new IllegalStateException("Token 정보가 존재하지 않습니다.");
            }
            token = token.split(" ")[1].trim();
        } catch (Exception e) {
            return null;
        }
        return token;
    }

    /*
        이 메소드는 토큰을 전달 받아 클레임의 유효기간을 체크하고 boolean 타입 값을 리턴하는 역할을 한다.
    */
    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효 체크 시작");
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKey).build().parseClaimsJws(token);

        if (!claims.getBody().getExpiration().before(new Date())) {
            log.info("[validateToken] 유효성 체크 성공");
            return true;
        } else {
            log.info("[validateToken] 유효성 체크 실패");
            return false;
        }
    }
}
