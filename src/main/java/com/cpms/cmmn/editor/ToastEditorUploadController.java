package com.cpms.cmmn.editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.cmmn.exception.CustomException;
import com.cpms.cmmn.response.ErrorCode;

@RestController
@RequestMapping("/cmmn/editor/toast-editor")
public class ToastEditorUploadController {

    @Value("${editor.file.upload.path}")
    private String fileUploadPath;

    @Value("${my-url}")
    private String serverUrl;

    @PostMapping("/upload")
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file)
            throws IOException {

        if (file.isEmpty()) throw new RuntimeException("파일이 비었습니다.");

        // 확장자 체크
        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();

        if (!ext.matches("png|jpg|jpeg|gif|webp")) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        // 날짜 기반 경로
        String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        Path folderPath = Paths.get(fileUploadPath, today);
        Files.createDirectories(folderPath);

        // 파일명 생성
        String newFileName = UUID.randomUUID() + "." + ext;
        Path savedPath = folderPath.resolve(newFileName);

        // 파일 저장
        Files.copy(file.getInputStream(), savedPath);

        // 접근 URL
        String fileUrl = serverUrl + "/resource/upload/editor/" + today + "/" + newFileName;

        // 응답
        Map<String, Object> result = new HashMap<>();
        result.put("success", 1);
        result.put("url", fileUrl);
        return result;
    }
}
