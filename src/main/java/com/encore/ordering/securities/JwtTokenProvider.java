package com.encore.ordering.securities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtTokenProvider {  //토큰생성 과정

    public String createToken(String email, String role) {
//        claims : 클레임은 토큰 사용자에 대한 속성이나 추가 데이터 포함.
        Claims claims = Jwts.claims().setSubject(email); // claims에 email, role 세팅
        claims.put("role", role);
        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 30 * 60 * 1000L))   // 30분 설정 // 30분 60초 1000밀리초
                .signWith(SignatureAlgorithm.HS256, "mysecret") //SHA256암호화 사용
                .compact();
        return token;
    }
}
