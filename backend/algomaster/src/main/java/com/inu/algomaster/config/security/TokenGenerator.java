package com.inu.algomaster.config.security;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    private static final SecureRandom secureRandom = new SecureRandom(); // SecureRandom 인스턴스 생성
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); // Base64 URL 인코더 생성

    public static String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes); // 랜덤 바이트 배열 생성
        return base64Encoder.encodeToString(randomBytes); // Base64 인코딩하여 문자열로 반환
    }

    public static void main(String[] args) {
        String token = generateToken();
        System.out.println("Generated Token: " + token); // 생성된 토큰 출력
    }
}
