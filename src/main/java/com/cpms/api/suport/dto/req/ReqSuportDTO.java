package com.cpms.api.suport.dto.req;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqSuportDTO {

    private Integer suportReqId;

    private Integer reqCompanyId;

    private Integer userCompanyId;

    private Integer reqProjectId;

    private Integer resUserId;

    private String requestCd;

    private String statusCd;

    private String reqDate;

    private String resDate;

    private String suportTitle;

    private String suportEditor;

    // 파일 데이터
    private MultipartFile[] suportFile;

    private Integer suportFileId;

    private String fileCategory;

    private String filePath;

    private String fileNm;

    private String fileOgNm;

    private String fileExt;

    private Long fileSize;

    // 메타 데이터
    private Integer userId;
}
