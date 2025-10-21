package com.cpms.api.support.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqInsertSupportDTO {

    private Integer supportRequestId;

    private Integer requestCompanyId;

    private Integer userCompanyId;

    private Integer requestProjectId;

    private Integer responseUserId;

    private Integer requestCd;

    private Integer statusCd;

    private String requestDate;

    private String responseDate;

    private String supportTitle;

    private String supportEditor;

    private MultipartFile[] supportFile;

    private Integer userId;
}
