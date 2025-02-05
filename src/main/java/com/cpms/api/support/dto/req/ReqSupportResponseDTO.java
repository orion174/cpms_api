package com.cpms.api.support.dto.req;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqSupportResponseDTO {

    private Integer supportRequestId;

    private Integer supportResponseId;

    private Integer responseStatusCd;

    private String responseTitle;

    private String responseEditor;

    private MultipartFile[] responseFile;

    private String fileCategory;

    private String filePath;

    private String fileNm;

    private String fileOgNm;

    private String fileExt;

    private Long fileSize;

    private Integer userId;
}
