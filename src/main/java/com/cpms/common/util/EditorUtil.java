package com.cpms.common.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditorUtil {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Value("${my-url}")
    private String serverUrl;

    /* 네이버 스마트 에디터 사진첨부 미리보기 서버 */
    @PostMapping("/util/Editor/smartEditorUploadURL")
    public void smarteditorMultiImageUpload(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String sFileInfo = "";
            String filename = request.getHeader("file-name");
            String filenameExt = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

            // 오늘 날짜 폴더 생성
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
            String todayFolder = dateFormatter.format(new java.util.Date());

            // 파일 저장 경로 설정 (editor/{오늘 날짜}/)
            String filePath =
                    fileUploadPath
                            + File.separator
                            + "editor"
                            + File.separator
                            + todayFolder
                            + File.separator;
            File folder = new File(filePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 파일명을 UUID로 저장
            String uniqueFileName =
                    UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
            String rlFileNm = filePath + uniqueFileName;

            // 서버에 파일 저장
            try (InputStream is = request.getInputStream();
                    OutputStream os = new FileOutputStream(rlFileNm)) {
                byte[] buffer = new byte[Integer.parseInt(request.getHeader("file-size"))];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }

            // 클라이언트로 반환할 이미지 경로 생성
            String fileURL =
                    serverUrl + "/resource/upload/editor/" + todayFolder + "/" + uniqueFileName;
            sFileInfo += "&bNewLine=true";
            sFileInfo += "&sFileName=" + filename;
            sFileInfo += "&sFileURL=" + fileURL;

            // 클라이언트로 파일 정보 전송
            PrintWriter print = response.getWriter();
            print.print(sFileInfo);
            print.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
