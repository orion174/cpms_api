package com.cpms.common.response;

public enum ResponseMessage {
    SUCCESS_MESSAGE("요청이 성공하였습니다."),

    REFRESH_SUCCESS("토큰 갱신에 성공했습니다."),

    LOGIN_SUCCESS("로그인에 성공했습니다."),

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
