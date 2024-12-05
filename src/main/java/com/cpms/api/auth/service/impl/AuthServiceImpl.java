package com.cpms.api.auth.service.impl;

import java.util.Optional;

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

import com.cpms.api.auth.dto.req.ReqLoginDTO;
import com.cpms.api.auth.dto.req.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.res.ResLoginDTO;
import com.cpms.api.auth.dto.res.ResRreshTokenDTO;
import com.cpms.api.auth.model.UserLoginHistory;
import com.cpms.api.auth.repository.AuthRepository;
import com.cpms.api.auth.repository.CustomAuthRepository;
import com.cpms.api.auth.repository.UserLoginHistoryRepository;
import com.cpms.api.auth.service.AuthService;
import com.cpms.common.helper.JwtDTO;
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

    /**
     * CPMS 로그인
     *
     * @param req
     * @param reqLoginDTO
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> userLogin(HttpServletRequest req, ReqLoginDTO reqLoginDTO) {
        // 로그인 아이디 검증
        ResLoginDTO resLoginDTO =
                customAuthRepository
                        .findUserByLoginId(reqLoginDTO.getLoginId())
                        .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        // 인증 검증 (비밀번호 체크)
        Authentication authentication = authenticate(reqLoginDTO);

        // 로그인 이력 추가 및 갱신
        UserLoginHistory loginHistory =
                UserLoginHistory.builder()
                        .userId(resLoginDTO.getUserId())
                        .loginId(resLoginDTO.getLoginId())
                        .accessIp(req.getRemoteAddr())
                        .build();

        // 생성된 loginHistoryId를 DTO에 반영
        UserLoginHistory savedHistory = loginHistoryRepository.saveAndFlush(loginHistory);
        resLoginDTO.setLoginHistoryId(savedHistory.getLoginHistoryId());

        // 인증정보 기반 토큰 생성
        JwtDTO jwtDTO = generateJwtToken(authentication, resLoginDTO);

        loginHistory.updateRefreshToken(jwtDTO.getRefreshToken());
        loginHistoryRepository.save(loginHistory);

        // 응답 DTO 생성
        ResLoginDTO result = buildResLoginDTO(resLoginDTO, jwtDTO);

        return new ResponseEntity<>(new ApiRes(result), HttpStatus.OK);
    }

    /**
     * 로그인 사용자 유효성 검증
     *
     * @param loginId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        ResLoginDTO resLoginDTO =
                customAuthRepository
                        .findUserByLoginId(loginId)
                        .orElseThrow(() -> new UsernameNotFoundException("아이디와 비밀번호가 일치하지 않습니다."));

        return User.builder()
                .username(resLoginDTO.getLoginId())
                .password(resLoginDTO.getLoginPw())
                .roles(resLoginDTO.getAuthType())
                .build();
    }

    /**
     * 리프레쉬 토큰을 발급한다.
     *
     * @param req
     * @param reqRefreshTokenDTO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> refreshToken(
            HttpServletRequest req, ReqRefreshTokenDTO reqRefreshTokenDTO) {

        String refreshToken = req.getHeader("X-Refresh-Token");

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiRes(false, "리프레쉬 토큰이 만료되었습니다."));
        }

        JwtDTO user = customAuthRepository.getUserInfoByLoginHistoryId(reqRefreshTokenDTO);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiRes(false, "유효하지 않은 사용자 정보입니다."));
        }

        JwtDTO newAccessToken = jwtTokenProvider.generateAccessToken(user);

        ResRreshTokenDTO result =
                ResRreshTokenDTO.builder()
                        .accessToken(newAccessToken.getAccessToken())
                        .accessTokenExpiration(newAccessToken.getAccessTokenExpiration())
                        .build();

        return new ResponseEntity<>(new ApiRes(result), HttpStatus.OK);
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
            throw new UsernameNotFoundException("아이디와 비밀번호가 일치하지 않습니다.");
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

        return Optional.of(resLoginDTO)
                .map(
                        user ->
                                JwtDTO.builder()
                                        .userId(user.getUserId())
                                        .companyId(user.getCompanyId())
                                        .build())
                .map(jwt -> jwtTokenProvider.generateToken(authentication, jwt))
                .orElseThrow(() -> new IllegalStateException("토큰 생성 실패"));
    }

    /**
     * 로그인 응답 객체생성
     *
     * @param resLoginDTO
     * @param jwtDTO
     * @return
     */
    private ResLoginDTO buildResLoginDTO(ResLoginDTO resLoginDTO, JwtDTO jwtDTO) {

        return ResLoginDTO.builder()
                .accessToken(jwtDTO.getAccessToken())
                .refreshToken(jwtDTO.getRefreshToken())
                .accessTokenExpiration(jwtDTO.getAccessTokenExpiration())
                .refreshTokenExpiration(jwtDTO.getRefreshTokenExpiration())
                .authType(resLoginDTO.getAuthType())
                .loginHistoryId(resLoginDTO.getLoginHistoryId())
                .userId(resLoginDTO.getUserId())
                .companyId(resLoginDTO.getCompanyId())
                .build();
    }
}
