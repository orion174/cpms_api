package com.cpms.api.support.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResSupportDetailDTO {

    private String authType;

    private Integer supportRequestId;

    private String requestCompanyNm;

    private Integer userCompanyId;

    private String userCompanyNm;

    private String requestProjectNm;

    private Integer requestCd;

    private String requestNm;

    private Integer statusCd;

    private String statusNm;

    private Integer responseUserId;

    private String responseUserNm;

    private String requestUserNm;

    private String requestDate;

    private String responseDate;

    private String supportTitle;

    private String supportEditor;

    private SupportResponse supportResponse;

    @Getter
    @Setter
    public static class SupportResponse {

        private Integer supportResponseId;

        private String responseTitle;

        private String responseEditor;
    }

    private List<FileList> fileList;

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
