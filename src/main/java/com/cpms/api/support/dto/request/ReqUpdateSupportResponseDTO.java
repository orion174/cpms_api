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
public class ReqUpdateSupportResponseDTO {

    private Integer responseStatusCd;

    private String responseTitle;

    private String responseEditor;

    private MultipartFile[] responseFile;
}
