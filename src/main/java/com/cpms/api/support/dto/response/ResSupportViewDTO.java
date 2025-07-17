package com.cpms.api.support.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResSupportViewDTO {
    private Integer supportRequestId;

    private String requestCompanyNm;

    private Integer userCompanyId;

    private String userCompanyNm;

    private String requestProjectNm;

    private String responseUserNm;

    private Integer requestCd;

    private String requestNm;

    private Integer statusCd;

    private String statusNm;

    private String requestDate;

    private String responseDate;

    private String supportTitle;

    private String supportEditor;

    private Integer regId;

    private SupportResponse supportResponse;

    private List<FileList> fileList;

    @Getter
    @Setter
    public static class SupportResponse {

        private Integer supportResponseId;

        private String responseTitle;

        private String responseEditor;
    }

    @Getter
    @Setter
    public static class FileList {

        private Integer supportFileId;

        private String fileType;

        private String filePath;

        private String fileNm;

        private String fileOgNm;
    }
}
