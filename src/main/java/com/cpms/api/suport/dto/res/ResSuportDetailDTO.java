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

    private String authType;

    private Integer suportReqId;

    private String reqCompanyNm;

    private Integer userCompanyId;

    private String userCompanyNm;

    private String reqProjectNm;

    private Integer requestCd;

    private String requestCdNm;

    private Integer statusCd;

    private String statusCdNm;

    private Integer resUserId;

    private String resUserNm;

    private String reqUserNm;

    private String reqDate;

    private String resDate;

    private String suportTitle;

    private String suportEditor;

    private SuportRes suportRes;

    private List<FileList> fileList;

    @Getter
    @Setter
    public static class SuportRes {

        private Integer suportResId;

        private String resTitle;

        private String resEditor;
    }

    @Getter
    @Setter
    public static class FileList {

        private Integer suportFileId;

        private String fileType;

        private String filePath;

        private String fileNm;

        private String fileOgNm;
    }
}
