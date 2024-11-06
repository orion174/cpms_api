package com.cpms.common.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;

    /* JWT 엑세스 토큰 만료시간 */
    @Value("${jwt.access.token.expiration}")
    int ACCESS_TOKEN_EXPIRATION;

    /* JWT 리프레시 토큰 만료시간 */
    @Value("${jwt.refresh.token.expiration}")
    int REFRESH_TOKEN_EXPIRATION;

    /*
     * JWT 서명을 위한 비밀키 설정
     * @param secretKey
     */
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /*
     * JWT 토큰 생성 (액세스 및 리프레시 토큰)f
     * @param authentication
     * @param jwtDTO
     * @return
     */
    public JwtDTO generateToken(Authentication authentication, JwtDTO jwtDTO) {
        // 권한 가져오기
        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        /* Access Token 생성 */
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRATION);

        Claims claims = Jwts.claims();

        claims.setSubject(authentication.getName());
        claims.put("auth", authorities);
        claims.put("userId", jwtDTO.getUserId());
        claims.put("companyId", jwtDTO.getCompanyId());
        claims.put("authType", jwtDTO.getAuthType());

        String accessToken =
                Jwts.builder()
                        .setSubject(authentication.getName())
                        .setClaims(claims)
                        .setExpiration(accessTokenExpiresIn)
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();

        /* Refresh Token 생성 */
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRATION);

        String refreshToken =
                Jwts.builder()
                        .setExpiration(refreshTokenExpiresIn)
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();

        return JwtDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpiration(ACCESS_TOKEN_EXPIRATION)
                .refreshToken(refreshToken)
                .refreshTokenExpiration(REFRESH_TOKEN_EXPIRATION)
                .build();
    }

    /*
     * 엑세스 토큰 생성
     * @param jwtDTO
     * @return
     */
    public JwtDTO generateAccessToken(JwtDTO jwtDTO) {
        long now = (new Date()).getTime();

        /* Access Token 생성*/
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRATION);

        Claims claims = Jwts.claims();

        claims.setSubject(jwtDTO.getLoginId());
        claims.put("auth", "ROLE_" + jwtDTO.getAuthType());
        claims.put("userId", jwtDTO.getUserId());
        claims.put("companyId", jwtDTO.getCompanyId());
        claims.put("authType", jwtDTO.getAuthType());

        String accessToken =
                Jwts.builder()
                        .setSubject(jwtDTO.getLoginId())
                        .setClaims(claims)
                        .setExpiration(accessTokenExpiresIn)
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();

        return JwtDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpiration(ACCESS_TOKEN_EXPIRATION)
                .build();
    }

    public JwtDTO generateRefreshToken(JwtDTO jwtDTO) {
        long now = (new Date()).getTime();

        // Refresh Token 생성
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRATION);

        String refreshToken =
                Jwts.builder()
                        .setExpiration(refreshTokenExpiresIn)
                        .signWith(key, SignatureAlgorithm.HS256)
                        .compact();

        return JwtDTO.builder()
                .grantType("Bearer")
                .refreshToken(refreshToken)
                .refreshTokenExpiration(REFRESH_TOKEN_EXPIRATION)
                .build();
    }

    /*
     * JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼냄
     * @param accessToken
     * @return
     */
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /*
     * 토큰 정보를 검증하는 메서드
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token");

        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token");

        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token");

        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.");
        }

        return false;
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getToken(String bearerToken) {
        String token = "";

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            token = bearerToken.substring(7);
        }

        return token;
    }

    public String getClaim(String key) {
        HttpServletRequest req =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                        .getRequest();
        Claims claims = parseClaims(getToken(req.getHeader("Authorization")));
        Object claim = claims.get(key);

        if (null != claim) {
            return claim.toString();
        } else {
            return "";
        }
    }
}
