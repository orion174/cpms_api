package com.cpms.common.util;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FileDTO {

    private String filePath;

    private String fileOgNm;

    private String fileNm;

    private String fileExt;

    private Long fileSize;
}
