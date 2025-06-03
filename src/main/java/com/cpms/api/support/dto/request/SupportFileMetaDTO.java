package com.cpms.api.support.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportFileMetaDTO {

    private Integer supportFileId;  // 파일 PK

    private String filePath;        // 저장 경로

    private String fileNm;          // 저장 파일명

    private String fileOgNm;        // 원본 파일명

    private String fileExt;         // 확장자

    private Long fileSize;          // 파일 크기

    private String fileCategory;    // 분류 (예: REQ / RES)
}