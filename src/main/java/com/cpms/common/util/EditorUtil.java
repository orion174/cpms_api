package com.cpms.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditorUtil {

    @Value("${editor.file.upload.path}")
    private String fileUploadPath;

    @Value("${my-url}")
    private String serverUrl;

    /* 네이버 스마트 에디터 사진첨부 미리보기 서버 */
    @PostMapping("/util/Editor/smartEditorUploadURL")
    public void smarteditorMultiImageUpload(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sFileInfo = "";
        String filename = request.getHeader("file-name");
        String filenameExt = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        // 오늘 날짜 폴더 생성
        String todayFolder = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());

        // 파일 저장 경로 설정 (editor/{오늘 날짜}/)
        Path folderPath = Paths.get(fileUploadPath, todayFolder);
        Files.createDirectories(folderPath); // 경로가 없으면 생성

        // 파일명을 UUID로 저장
        String uniqueFileName = UUID.randomUUID() + "." + filenameExt;
        Path filePath = folderPath.resolve(uniqueFileName);

        // 서버에 파일 저장
        try (InputStream is = request.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
            is.transferTo(os); // InputStream을 OutputStream으로 직접 전송
        }

        // 클라이언트로 반환할 이미지 경로 생성
        String fileURL =
                serverUrl + "/resource/upload/editor/" + todayFolder + "/" + uniqueFileName;
        sFileInfo = String.format("&bNewLine=true&sFileName=%s&sFileURL=%s", filename, fileURL);

        // 클라이언트로 파일 정보 전송
        try (PrintWriter print = response.getWriter()) {
            print.print(sFileInfo);
        }
    }
}
