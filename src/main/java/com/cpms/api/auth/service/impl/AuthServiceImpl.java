package com.cpms.api.auth.service.impl;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

import com.cpms.api.auth.dto.request.ReqLoginDTO;
import com.cpms.api.auth.dto.request.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.response.ResAuthDTO;
import com.cpms.api.auth.dto.response.ResLoginDTO;
import com.cpms.api.auth.dto.response.ResRefreshTokenDTO;
import com.cpms.api.auth.model.UserLoginHistory;
import com.cpms.api.auth.repository.AuthRepository;
import com.cpms.api.auth.repository.CustomAuthRepository;
import com.cpms.api.auth.repository.UserLoginHistoryRepository;
import com.cpms.api.auth.service.AuthService;
import com.cpms.cmmn.config.CorsProperties;
import com.cpms.cmmn.exception.CustomException;
import com.cpms.cmmn.helper.EntityFinder;
import com.cpms.cmmn.helper.JwtDTO;
import com.cpms.cmmn.jwt.JwtTokenProvider;
import com.cpms.cmmn.response.ErrorCode;
import com.cpms.cmmn.util.CookieUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    private final EntityFinder entityFinder;

    private final AuthRepository authRepository;

    private final CustomAuthRepository customAuthRepository;

    private final UserLoginHistoryRepository loginHistoryRepository;

    private final CorsProperties corsProperties;

    /**
     * CPMS 로그인
     *
     * @param request
     * @param response
     * @param reqLoginDTO
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResLoginDTO> userLogin(
            HttpServletRequest request, HttpServletResponse response, ReqLoginDTO reqLoginDTO) {
        // 사용자 정보
        ResLoginDTO resLoginDTO =
                entityFinder.findByOptionalOrThrow(
                        customAuthRepository.findUserByLoginId(reqLoginDTO.getLoginId()),
                        ErrorCode.USER_NOT_FOUND);

        // 사용자 검증
        Authentication authentication = authenticate(reqLoginDTO);

        UserLoginHistory loginHistory =
                UserLoginHistory.builder()
                        .userId(resLoginDTO.getUserId())
                        .loginId(resLoginDTO.getLoginId())
                        .accessIp(request.getRemoteAddr())
                        .build();

        // 로그인 이력 저장
        UserLoginHistory savedHistory = loginHistoryRepository.saveAndFlush(loginHistory);
        resLoginDTO.setLoginHistoryId(savedHistory.getLoginHistoryId());

        // 로그인한 정보를 바탕으로 토큰을 생성한다.
        JwtDTO jwtDTO = generateJwtToken(authentication, resLoginDTO);
        loginHistory.updateRefreshToken(jwtDTO.getRefreshToken());
        loginHistoryRepository.save(loginHistory);

        // 리프레쉬 토큰 저장
        int refreshExp = jwtDTO.getRefreshTokenExpiration() / 1000;
        CookieUtil.saveRefreshCookie(request, response, jwtDTO.getRefreshToken(), refreshExp);

        ResLoginDTO result =
                ResLoginDTO.builder()
                        .accessToken(jwtDTO.getAccessToken())
                        .accessTokenExpiration(jwtDTO.getAccessTokenExpiration())
                        .loginHistoryId(savedHistory.getLoginHistoryId())
                        .build();

        return ResponseEntity.ok(result);
    }

    /**
     * Access 토큰 재발급
     *
     * @param refreshToken
     * @param reqDto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResRefreshTokenDTO refreshToken(
            @CookieValue("refreshToken") String refreshToken,
            @RequestBody ReqRefreshTokenDTO reqDto) {
        // 토큰이 유효한지 검사
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        // 로그인 이력 검사
        JwtDTO user = customAuthRepository.getUserInfoByLoginHistoryId(reqDto);

        if (user == null) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        JwtDTO newAccessToken = jwtTokenProvider.updateAccessToken(user);

        return ResRefreshTokenDTO.builder()
                .accessToken(newAccessToken.getAccessToken())
                .accessTokenExpiration(newAccessToken.getAccessTokenExpiration())
                .build();
    }

    /**
     * 로그인 사용자 유효성 검증
     *
     * @param loginId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) {
        ResLoginDTO resLoginDTO =
                entityFinder.findByOptionalOrThrow(
                        customAuthRepository.findUserByLoginId(loginId), ErrorCode.USER_NOT_FOUND);

        return User.builder()
                .username(resLoginDTO.getLoginId())
                .password(resLoginDTO.getLoginPw())
                .roles(resLoginDTO.getAuthType())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResAuthDTO> seletCpmsAuthList() {
        return customAuthRepository.selectCpmsAuthList();
    }

    /**
     * 사용자의 아이디와 비밀번호를 검증한다.
     *
     * @param reqLoginDTO
     * @return
     */
    private Authentication authenticate(ReqLoginDTO reqLoginDTO) {
        try {
            return authenticationManagerBuilder
                    .getObject()
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    reqLoginDTO.getLoginId(), reqLoginDTO.getLoginPw()));

        } catch (Exception e) {
            throw new CustomException(ErrorCode.USER_UNAUTHORIZED);
        }
    }

    /**
     * 인증된 정보를 기반으로 토큰을 발급한다.
     *
     * @param authentication
     * @param resLoginDTO
     * @return
     */
    private JwtDTO generateJwtToken(Authentication authentication, ResLoginDTO resLoginDTO) {
        if (resLoginDTO == null) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR);
        }

        JwtDTO jwtDto =
                JwtDTO.builder()
                        .userId(resLoginDTO.getUserId())
                        .authType(resLoginDTO.getAuthType())
                        .companyId(resLoginDTO.getCompanyId())
                        .loginId(resLoginDTO.getLoginId())
                        .build();

        return jwtTokenProvider.generateAccessToken(authentication, jwtDto);
    }
}
