package com.cpms.api.suport.dto.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResSuportDetailDTO {

    private Integer suportReqId;

    private Integer reqCompanyId;

    private Integer userCompanyId;

    private Integer reqProjectId;

    private Long resUserId;

    private String requestCd;

    private String statusCd;

    private String reqDate;

    private String resDate;

    private String suportTitle;

    private String suportEditor;

    private Integer suportResId;

    private String resEditor;

    private List<FileList> fileList;

    @Getter
    @Setter
    public static class FileList {

        private Integer suportFileId;

        private Integer suportReqId;

        private String fileType;

        private String filePath;

        private String fileNm;

        private String fileOgNm;

        private String fileExt;

        private Long fileSize;
    }
}
