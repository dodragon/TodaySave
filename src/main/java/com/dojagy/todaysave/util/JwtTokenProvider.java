package com.dojagy.todaysave.util;

import com.dojagy.todaysave.data.dto.TokenInfo;
import com.dojagy.todaysave.data.dto.user.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;

    public JwtTokenProvider(@Value("${dojagy.today.save.app.jwt.secret}") String secretKey,
                            @Value("${dojagy.today.save.app.jwt.access-token-milliseconds}") long accessTokenValidity,
                            @Value("${dojagy.today.save.app.jwt.refresh-token-milliseconds}") long refreshTokenValidity) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidityInMilliseconds = accessTokenValidity;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidity;
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfo generateToken(Authentication authentication) {
        // 1. Principal에서 UserPrincipal 객체 추출
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + accessTokenValidityInMilliseconds);
        String accessToken = Jwts.builder()
                // .setSubject(authentication.getName()) // 더 이상 사용하지 않음
                .claim("id", userPrincipal.id())       // 커스텀 클레임으로 id 추가
                .claim("email", userPrincipal.email()) // 커스텀 클레임으로 email 추가
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성 (Refresh Token에는 보통 만료 시간 외에 다른 정보를 담지 않음)
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // --- 수정된 부분 ---
        // 1. 클레임에서 id와 email 정보 추출
        Long id = claims.get("id", Long.class);
        String email = claims.get("email", String.class);

        // 2. 추출한 정보로 UserPrincipal 객체 생성
        UserPrincipal principal = new UserPrincipal(id, email);

        // 3. UserPrincipal 객체를 담아 Authentication 객체 생성
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

