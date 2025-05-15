package com.cpms.common.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private List<String> allowedOrigins;

    public static String extractDomain(String origin) {
        try {
            URI uri = new URI(origin);
            return uri.getHost();

        } catch (URISyntaxException e) {
            return "localhost";
        }
    }
}
