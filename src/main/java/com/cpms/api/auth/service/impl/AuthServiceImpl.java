package com.cpms.api.auth.service.impl;

import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.api.auth.dto.req.*;
import com.cpms.api.auth.dto.res.*;
import com.cpms.api.auth.model.UserLoginHistory;
import com.cpms.api.auth.repository.AuthRepository;
import com.cpms.api.auth.repository.CustomAuthRepository;
import com.cpms.api.auth.repository.UserLoginHistoryRepository;
import com.cpms.api.auth.service.AuthService;
import com.cpms.common.jwt.JwtDTO;
import com.cpms.common.jwt.JwtTokenProvider;
import com.cpms.common.res.ApiRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthRepository authRepository;

    private final CustomAuthRepository customAuthRepository;

    private final UserLoginHistoryRepository loginHistoryRepository;

    @Override
    @Transactional
    public ResLoginDTO userLogin(HttpServletRequest request, ReqLoginDTO reqLoginDTO) {
        /* 사용자 정보 가져오기 (loginId 기반 조회) */
        ResLoginDTO userDto =
                customAuthRepository
                        .findUserByLoginId(reqLoginDTO.getLoginId())
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "ERR_NOT_FOUND")); // 아이디가 맞지 않을 때

        /* 로그인 ID/PW로 Authentication 객체 생성 */
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        reqLoginDTO.getLoginId(), reqLoginDTO.getLoginPw());

        /* 인증 검증 (PW 체크) */
        Authentication authentication;
        try {
            authentication =
                    authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (Exception e) {
            // 비밀번호가 맞지 않을 때
            throw new UsernameNotFoundException("ERR_NOT_FOUND");
        }

        /* 로그인 이력 추가 */
        String accessIp = request.getRemoteAddr();

        UserLoginHistory loginHistory =
                new UserLoginHistory(userDto.getUserId(), userDto.getLoginId(), accessIp);
        loginHistoryRepository.save(loginHistory);

        /* 인증정보 기반 JWT 토큰 생성 */
        JwtDTO jwtDTO =
                JwtDTO.builder()
                        .userId(userDto.getUserId())
                        .authType(userDto.getAuthType())
                        .companyId(userDto.getCompanyId())
                        .loginId(userDto.getLoginId())
                        .build();

        jwtDTO = jwtTokenProvider.generateToken(authentication, jwtDTO);

        /* RefreshToken 업데이트 */
        loginHistory.updateRefreshToken(jwtDTO.getRefreshToken());
        loginHistoryRepository.save(loginHistory); // 로그인 이력 업데이트

        return ResLoginDTO.builder()
                .userId(userDto.getUserId())
                .authType(userDto.getAuthType())
                .companyId(userDto.getCompanyId())
                .loginId(userDto.getLoginId())
                .loginHistoryId(loginHistory.getLoginHistoryId())
                .useYn(userDto.getUseYn())
                .accessToken(jwtDTO.getAccessToken())
                .accessTokenExpiration(jwtDTO.getAccessTokenExpiration())
                .refreshToken(jwtDTO.getRefreshToken())
                .refreshTokenExpiration(jwtDTO.getRefreshTokenExpiration())
                .option("login")
                .build();
    }

    /*
     * 입력된 아이디를 통한 사용자 정보를 반환한다.
     * @param loginId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        ResLoginDTO userDto =
                customAuthRepository
                        .findUserByLoginId(loginId)
                        .orElseThrow(() -> new UsernameNotFoundException("ERR_NOT_FOUND"));

        return User.builder()
                .username(userDto.getLoginId())
                .password(userDto.getLoginPw())
                .roles(userDto.getAuthType())
                .build();
    }

    @Override
    public ResponseEntity<?> refreshToken(ReqRefreshTokenDTO reqRefreshTokenDTO) {
        Object result;

        // 토큰 유효성 검증
        if (jwtTokenProvider.validateToken(reqRefreshTokenDTO.getRefreshToken())) {
            JwtDTO jwtDTO = customAuthRepository.getUserInfoByLoginHistoryId(reqRefreshTokenDTO);

            // 유저 정보가 있을 경우
            if (!Objects.isNull(jwtDTO)) {
                jwtDTO = jwtTokenProvider.generateAccessToken(jwtDTO);

                Map<String, Object> resultMap = new HashMap<>();

                resultMap.put("accessToken", jwtDTO.getAccessToken());
                resultMap.put("accessTokenExpiration", jwtDTO.getAccessTokenExpiration());

                result = resultMap;

            } else {
                // 유저 정보 없음
                result = false;
            }
        } else {
            // 토큰 유효성 검사 실패
            result = false;
        }

        return new ResponseEntity<>(
                new ApiRes(result),
                Boolean.FALSE.equals(result) ? HttpStatus.UNAUTHORIZED : HttpStatus.OK);
    }
}
