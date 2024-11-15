package com.cpms.common.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.common.helper.FileDTO;

public class FileUtil {

    public static FileDTO fileUpload(MultipartFile multipartFile, String filePath)
            throws Exception {
        File fileDir = new File(filePath);

        if (!fileDir.exists()) {
            if (!fileDir.mkdirs()) {
                throw new Exception(
                        "file upload failed : 파일 디렉토리 생성에 실패했습니다.[failed file path : "
                                + filePath
                                + "]");
            }
        }

        // UUID.randomUUID()를 이용해서 고유한 파일 이름을 만들어냄
        String fileOgNm = multipartFile.getOriginalFilename();
        String fileExt = FilenameUtils.getExtension(fileOgNm);
        String fileNm = UUID.randomUUID() + "." + fileExt;

        // 실제 파일 저장
        multipartFile.transferTo(new File(filePath + "/" + fileNm));

        return FileDTO.builder()
                .filePath(filePath)
                .fileOgNm(fileOgNm)
                .fileExt(fileExt)
                .fileNm(fileNm)
                .fileSize(multipartFile.getSize())
                .build();
    }

    public static ResponseEntity<?> fileDownload(String filePath, String fileName) {
        try {
            Path file = Paths.get(filePath).resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            byte[] content = Files.readAllBytes(file);

            return ResponseEntity.ok().body(content);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
