package com.cpms.common.helper;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileDTO {

    private String filePath;

    private String fileOgNm;

    private String fileNm;

    private String fileExt;

    private Long fileSize;
}
