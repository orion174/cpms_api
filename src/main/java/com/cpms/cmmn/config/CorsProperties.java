package com.cpms.cmmn.config;

import java.net.URI;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private List<String> allowedOrigins;

    // 설정된 allowedOrigins[0] 기준으로 domain 자동 추출
    public String extractDomain() {
        if (allowedOrigins == null || allowedOrigins.isEmpty()) {
            return null;
        }

        try {
            String origin = allowedOrigins.get(0);
            String host = new URI(origin).getHost();

            // duckdns, 도메인이면 상위 도메인 추출
            if (host != null && host.endsWith(".duckdns.org")) {
                return ".duckdns.org";
            }

            // 일반 도메인이라면 그대로 반환
            return host;
        } catch (Exception e) {
            return null;
        }
    }
}
