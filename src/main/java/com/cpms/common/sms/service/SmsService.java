package com.cpms.common.sms.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cpms.common.sms.dto.SmsDTO;

@Service
public class SmsService {

    @Value("${directsend.id}")
    public String id;

    @Value("${directsend.key}")
    public String key;

    @Value("${directsend.sender.phone}")
    public String sender;

    public int sendSMS(SmsDTO smsDTO) {
        String url = "https://directsend.co.kr/index.php/api_v2/sms_change_word";

        try {
            URL obj;
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("Accept", "application/json");

            String message = smsDTO.getMessage();
            String receiver = "{\"mobile\":\"" + smsDTO.getReceiver() + "\"}";
            receiver = "[" + receiver + "]";

            String urlParameters =
                    "\"message\":\""
                            + message
                            + "\" "
                            + ", \"sender\":\""
                            + sender
                            + "\" "
                            + ", \"username\":\""
                            + id
                            + "\" "
                            + ", \"receiver\":"
                            + receiver
                            + ", \"key\":\""
                            + key
                            + "\" "
                            + ", \"type\":\""
                            + "java"
                            + "\" ";
            urlParameters = "{" + urlParameters + "}";

            System.setProperty("jsse.enableSNIExtension", "false");
            con.setDoOutput(true);
            OutputStreamWriter wr =
                    new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8);
            wr.write(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();

            java.io.BufferedReader in =
                    new java.io.BufferedReader(
                            new java.io.InputStreamReader(
                                    con.getInputStream(), StandardCharsets.UTF_8));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            JSONParser parser = new JSONParser();
            JSONObject responseParse = (JSONObject) parser.parse(response.toString());

            String status;

            // 응답 status가 0이 아니면 전부 실패
            if (null != responseParse && null != responseParse.get("status")) {
                status = String.valueOf(responseParse.get("status"));

                if (!status.equals("0")) {
                    return -1;
                }

            } else {
                return -1;
            }

            return Integer.parseInt(status);

            /*
             * response의 실패
             * {"status":113, "msg":"UTF-8 인코딩이 아닙니다."}
             * 실패 코드번호, 내용
             * status code 112 실패인 경우 인코딩 실패 문자열 return
             *  {"status":112, "msg": "message EUC-KR 인코딩에 실패 하였습니다.", "msg_detail":(13)}
             *  실패 코드번호, 내용, 인코딩 실패 문자열(문자열 위치)
             */

            /*
             * response 성공
             * {"status":0}
             * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.)
             * 잘못된 번호가 포함된 경우
             * {"status":0, "msg":"유효하지 않는 번호를 제외하고 발송 완료 하였습니다.", "msg_detail":"error mobile : 01000000001aa, 010112"}
             * 성공 코드번호 (성공코드는 다이렉트센드 DB서버에 정상수신됨을 뜻하며 발송성공(실패)의 결과는 발송완료 이후 확인 가능합니다.), 내용, 잘못된 데이터
             *
             */

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
