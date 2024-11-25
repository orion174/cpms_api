package com.cpms.api.suport.dto.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqSuportResDTO {

    private Integer suportReqId;

    private String resStatusCd;

    private String resEditor;

    // 파일 데이터
    private MultipartFile[] resFile;

    private String fileCategory;

    private String filePath;

    private String fileNm;

    private String fileOgNm;

    private String fileExt;

    private Long fileSize;

    // 메타 데이터
    private Integer userId;
}
