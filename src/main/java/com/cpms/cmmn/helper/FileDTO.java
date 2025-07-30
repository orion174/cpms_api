package com.cpms.cmmn.helper;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDTO {

    private String filePath;

    private String fileNm;

    private String fileOgNm;

    private String fileExt;

    private Long fileSize;
}
