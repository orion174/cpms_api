package com.cpms.common.jwt;

import static com.cpms.common.helper.Constants.*;
import static org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor.BEARER;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cpms.common.helper.JwtDTO;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.access.token.expiration}")
    int ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.refresh.token.expiration}")
    int REFRESH_TOKEN_EXPIRATION;

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    /**
     * Jwt 토큰 부분을 추출한다.
     *
     * @param bearerToken
     * @return
     */
    public String getToken(String bearerToken) {
        return Optional.ofNullable(bearerToken)
                .filter(token -> StringUtils.hasText(token))
                .filter(token -> token.startsWith(BEARER_PREFIX))
                .map(token -> token.substring(7))
                .orElse("");
    }

    /**
     * 토큰에서 클레임 데이터를 가져온다.
     *
     * @param token
     * @param key
     * @return
     */
    public String getClaim(String token, String key) {
        return Optional.ofNullable(token)
                .map(this::parseClaims)
                .map(claims -> claims.get(key))
                .map(Object::toString)
                .orElse(null);
    }

    /**
     * 토큰을 생성한다.
     *
     * @param authentication
     * @param jwtDTO
     * @return
     */
    public JwtDTO generateAccessToken(Authentication authentication, JwtDTO jwtDTO) {
        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

        Claims claims = createClaims(authentication.getName(), jwtDTO, authorities);

        return JwtDTO.builder()
                .grantType(BEARER)
                .authType(jwtDTO.getAuthType())
                .userId(jwtDTO.getUserId())
                .companyId(jwtDTO.getCompanyId())
                .loginId(jwtDTO.getLoginId())
                .accessToken(createAccessToken(claims))
                .refreshToken(createRefreshToken())
                .accessTokenExpiration(ACCESS_TOKEN_EXPIRATION)
                .refreshTokenExpiration(REFRESH_TOKEN_EXPIRATION)
                .build();
    }

    /**
     * 엑세스 토큰 재발급
     *
     * @param jwtDTO
     * @return
     */
    public JwtDTO updateAccessToken(JwtDTO jwtDTO) {
        String authorities = "ROLE_" + jwtDTO.getAuthType(); // 스프링 시큐리티 권한 설정
        Claims claims = createClaims(jwtDTO.getLoginId(), jwtDTO, authorities);

        return JwtDTO.builder()
                .grantType(BEARER)
                .accessToken(createAccessToken(claims))
                .accessTokenExpiration(ACCESS_TOKEN_EXPIRATION)
                .build();
    }

    /**
     * 토큰을 복호화하여 정보를 꺼낸다.
     *
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        Collection<? extends GrantedAuthority> authorities =
                Optional.ofNullable(claims.get(CLAIM_AUTH))
                        .map(Object::toString)
                        .map(
                                auth ->
                                        Arrays.stream(auth.split(","))
                                                .map(SimpleGrantedAuthority::new)
                                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * 토큰을 복호화하여 클레임 정보를 추출한다.
     *
     * @param token
     * @return
     */
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * 토큰 정보를 검증하는 메서드
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 토큰", e.getMessage());

        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰", e.getMessage());

        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 토큰", e.getMessage());

        } catch (IllegalArgumentException e) {
            log.info("형식이 잘못된 토큰", e.getMessage());
        }

        return false;
    }

    /**
     * Jwt 토큰에 사용자 정보를 저장한다.
     *
     * @param subject
     * @param jwtDTO
     * @param authorities
     * @return
     */
    private Claims createClaims(String subject, JwtDTO jwtDTO, String authorities) {
        Map<String, Object> claimMap = new HashMap<>();

        claimMap.put(CLAIM_AUTH_TYPE, jwtDTO.getAuthType());
        claimMap.put(CLAIM_USER_ID, jwtDTO.getUserId());
        claimMap.put(CLAIM_COMPANY_ID, jwtDTO.getCompanyId());
        claimMap.put(CLAIM_LOGIN_ID, jwtDTO.getLoginId());

        if (authorities != null) {
            claimMap.put(CLAIM_AUTH, authorities);
        }

        return Jwts.claims(claimMap).setSubject(subject);
    }

    /**
     * access 토큰을 생성한다.
     *
     * @param claims
     * @return
     */
    private String createAccessToken(Claims claims) {
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + ACCESS_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * refresh 토큰을 생성한다.
     *
     * @return
     */
    private String createRefreshToken() {
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + REFRESH_TOKEN_EXPIRATION);

        return Jwts.builder()
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
