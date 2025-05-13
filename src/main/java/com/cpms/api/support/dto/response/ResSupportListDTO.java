package com.cpms.api.support.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResSupportListDTO {

    private int totalCnt;

    private String authType;

    private List<SupportList> supportList;

    @Getter
    @Setter
    public static class SupportList {

        private Integer supportRequestId;

        private String userCompanyNm; // 요청자 회사 명

        private String requestProjectNm; // 요청 프로젝트 명

        private String responseUserNm; // 처리 담당자

        private Integer requestCd; // 요청 유형 코드

        private String requestNm; // 요청 유형

        private Integer statusCd; // 처리 상태 코드

        private String statusNm; // 처리 상태

        private String requestDate; // 처리기한

        private String supportTitle; // 제목

        private String regDt; // 등록일
    }
}
