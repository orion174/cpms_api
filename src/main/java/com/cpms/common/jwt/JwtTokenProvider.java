package com.cpms.common.jwt;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    private static final String AUTH_HEADER = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

    private static final String BEARER = "Bearer";

    /**
     * 토큰 서명을 위한 비밀키를 설정한다.
     *
     * @param secretKey
     */
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
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
     * @param key
     * @return
     */
    public String getClaim(String key) {

        return Optional.ofNullable(RequestContextHolder.currentRequestAttributes())
                .filter(attr -> attr instanceof ServletRequestAttributes) // 요청 속성이
                // ServletRequestAttributes인지
                // 확인한다.
                .map(attr -> (ServletRequestAttributes) attr)
                .map(ServletRequestAttributes::getRequest) // HttpServletRequest  객체를 가져온다.
                .map(req -> req.getHeader(AUTH_HEADER)) // 요청 헤더에서 "Authorization" 값을 가져온다.
                .map(this::getToken) // Jwt 토큰 추출
                .map(this::parseClaims) // 토큰 데이터 추출
                .map(claims -> claims.get(key)) // key에 매핑된 데이터 추출
                .map(Object::toString)
                .orElse("");
    }

    /**
     * 토큰을 생성한다.
     *
     * @param authentication
     * @param jwtDTO
     * @return
     */
    public JwtDTO generateToken(Authentication authentication, JwtDTO jwtDTO) {

        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

        Claims claims = createClaims(authentication.getName(), jwtDTO, authorities);

        return JwtDTO.builder()
                .grantType("Bearer")
                .accessToken(createAccessToken(claims))
                .refreshToken(createRefreshToken())
                .accessTokenExpiration(ACCESS_TOKEN_EXPIRATION)
                .refreshTokenExpiration(REFRESH_TOKEN_EXPIRATION)
                .build();
    }

    /**
     * 엑세스 토큰 생성
     *
     * @param jwtDTO
     * @return
     */
    public JwtDTO generateAccessToken(JwtDTO jwtDTO) {

        Claims claims = createClaims(jwtDTO.getLoginId(), jwtDTO, null);

        return JwtDTO.builder()
                .grantType(BEARER)
                .accessToken(createAccessToken(claims))
                .accessTokenExpiration(ACCESS_TOKEN_EXPIRATION)
                .build();
    }

    /**
     * 리프레쉬 토큰 생성
     *
     * @return
     */
    public JwtDTO generateRefreshToken() {

        return JwtDTO.builder()
                .grantType(BEARER)
                .refreshToken(createRefreshToken())
                .refreshTokenExpiration(REFRESH_TOKEN_EXPIRATION)
                .build();
    }

    /**
     * 토큰을 복호화하여 정보를 꺼낸다.
     *
     * @param accessToken
     * @return
     */
    public Authentication getAuthentication(String accessToken) {

        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities =
                Optional.ofNullable(claims.get("auth"))
                        .map(Object::toString)
                        .map(
                                auth ->
                                        Arrays.stream(auth.split(","))
                                                .map(SimpleGrantedAuthority::new)
                                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * 토큰을 복호화하여 클레임 정보를 추출한다.
     *
     * @param accessToken
     * @return
     */
    public Claims parseClaims(String accessToken) {
        return Optional.ofNullable(accessToken)
                .map(
                        token -> {
                            try {
                                return Jwts.parserBuilder()
                                        .setSigningKey(key)
                                        .build()
                                        .parseClaimsJws(token)
                                        .getBody();

                            } catch (ExpiredJwtException e) {

                                return e.getClaims();
                            }
                        })
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
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
            log.info("유효하지 않은 토큰");

        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰");

        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 토큰");

        } catch (IllegalArgumentException e) {
            log.info("형식이 잘못된 토큰");
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

        Claims claims = Jwts.claims().setSubject(subject);

        if (authorities != null) {
            claims.put("auth", authorities);
        }

        claims.put("authType", "ROLE_" + jwtDTO.getAuthType());
        claims.put("userId", jwtDTO.getUserId());
        claims.put("companyId", jwtDTO.getCompanyId());

        return claims;
    }

    /**
     * 어세스 토큰을 생성한다.
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
     * 리프레쉬 토큰을 생성한다.
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
