package com.cpms.common.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.cpms.common.exception.CustomException;
import com.cpms.common.helper.FileDTO;
import com.cpms.common.response.ErrorCode;

public class FileUtil {

    public static FileDTO fileUpload(MultipartFile multipartFile, String filePath) {
        File fileDir = new File(filePath);

        if (!fileDir.exists() && !fileDir.mkdirs()) {
            throw new CustomException(ErrorCode.FILE_DIRECTORY_CREATE_FAILED);
        }

        String fileOgNm = multipartFile.getOriginalFilename();

        String fileExt = FilenameUtils.getExtension(fileOgNm);

        String fileNm = UUID.randomUUID() + "." + fileExt;

        File destFile = new File(fileDir, fileNm);

        try {
            multipartFile.transferTo(destFile);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_SAVE_FAILED);
        }

        return FileDTO.builder()
                .filePath(fileDir.getPath())
                .fileOgNm(fileOgNm)
                .fileExt(fileExt)
                .fileNm(fileNm)
                .fileSize(multipartFile.getSize())
                .build();
    }

    public static ResponseEntity<Resource> fileDownload(String filePath, String fileName) {
        try {
            Path file = Paths.get(filePath).resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                throw new CustomException(ErrorCode.FILE_NOT_FOUND);
            }

            String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + encodedFileName + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            throw new CustomException(ErrorCode.FILE_DOWNLOAD_FAILED);
        }
    }
}
