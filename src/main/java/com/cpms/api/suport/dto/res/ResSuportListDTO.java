package com.cpms.api.suport.dto.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResSuportListDTO {

    private int suportCnt;

    private List<SuportList> suportList;

    private String authType;

    @Getter
    @Setter
    public static class SuportList {

        private Integer suportReqId;

        private String userCompanyNm; // 요청자 회사 명

        private String reqProjectNm; // 요청 프로젝트 명

        private Integer requestCd; // 요청 유형

        private String requestCdNm; // 요청 유형

        private Integer statusCd; // 처리상태

        private String statusCdNm; // 처리상태

        private String resUserNm; // 담당자

        private String regDt; // 등록일

        private String reqDate; // 처리기한

        private String suportTitle;
    }
}
