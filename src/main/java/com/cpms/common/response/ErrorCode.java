package com.cpms.common.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "1001", "아이디 또는 비밀번호가 일치하지 않습니다."),

    NO_AUTHORITY(HttpStatus.FORBIDDEN, "1002", "권한이 없습니다."),

    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "1003", "리프레시 토큰이 만료되었습니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "1004", "인증되지 않았습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "2001", "존재하지 않는 사용자입니다."),

    INVALID_USER(HttpStatus.FORBIDDEN, "2002", "유효하지 않은 사용자 정보입니다."),

    INVALID_REQUEST_ID(HttpStatus.BAD_REQUEST, "3001", "유효하지 않은 요청 ID입니다."),

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "3002", "입력 값이 잘못되었습니다."),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "3003", "요청한 엔티티를 찾을 수 없습니다."),

    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "4001", "파일 업로드에 실패했습니다."),

    FILE_DIRECTORY_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "4002", "파일 디렉토리 생성에 실패했습니다."),

    FILE_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "4003", "파일 저장에 실패했습니다."),

    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "4004", "요청한 파일이 존재하지 않습니다."),

    FILE_DOWNLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "4005", "파일 다운로드에 실패했습니다."),

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
