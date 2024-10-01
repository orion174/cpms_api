package com.cpms.common.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EditorUtil {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Value("${my-url}")
    private String serverUrl;

    /* 네이버 스마트 에디터 사진첨부 미리보기 서버 */
    @RequestMapping(value = "/util/Editor/smartEditorUploadURL", method = RequestMethod.POST)
    public void smarteditorMultiImageUpload(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String sFileInfo = "";
            String filename = request.getHeader("file-name");
            String filename_ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

            // 파일 저장 경로 설정
            String filePath = fileUploadPath + File.separator + "editor" + File.separator;
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            // 파일명을 UUID로 저장
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String today = formatter.format(new java.util.Date());
            String realFileNm =
                    today
                            + UUID.randomUUID().toString()
                            + filename.substring(filename.lastIndexOf("."));

            // 파일 저장 경로
            String rlFileNm = filePath + realFileNm;

            // 서버에 파일 저장
            InputStream is = request.getInputStream();
            OutputStream os = new FileOutputStream(rlFileNm);
            byte[] b = new byte[Integer.parseInt(request.getHeader("file-size"))];
            int numRead;
            while ((numRead = is.read(b, 0, b.length)) != -1) {
                os.write(b, 0, numRead);
            }
            is.close();
            os.flush();
            os.close();

            // 서버 URL 앞에 붙여서 반환할 이미지 경로 생성
            String fileURL = serverUrl + "/resource/upload/editor/" + realFileNm; // 서버 URL을 붙임
            sFileInfo += "&bNewLine=true";
            sFileInfo += "&sFileName=" + filename;
            sFileInfo += "&sFileURL=" + fileURL; // 클라이언트로 반환할 URL

            // 클라이언트로 파일 정보 전송
            PrintWriter print = response.getWriter();
            print.print(sFileInfo);
            print.flush();
            print.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
