package com.cpms.common.response;

public enum ResponseMessage {
    SUCCESS_MESSAGE("요청이 성공하였습니다."),

    REFRESH_SUCCESS("토큰 갱신에 성공했습니다."),

    SEND_SMS_SUCCESS("인증 메세지 발송에 성공하였습니다."),

    PHONE_AUTH_SUCCESS("휴대폰 인증에 성공하였습니다."),

    ID_CHECK_SUCCESS("사용 가능한 아이디입니다. 해당 아이디를 사용하겠습니까?"),

    REGISTER_SUCCESS("회원가입이 성공했습니다. 로그인 화면으로 이동합니다."),

    LOGIN_SUCCESS("로그인에 성공했습니다."),

    COOKIE_DELETE_SUCCESS("쿠키 삭제 성공"),

    INSERT_SUCCESS("성공적으로 등록되었습니다."),

    UPDATE_SUCCESS("성공적으로 수정되었습니다."),

    DELETE_SUCCESS("성공적으로 삭제되었습니다."),

    SELECT_SUCCESS("데이터를 성공적으로 조회했습니다.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
