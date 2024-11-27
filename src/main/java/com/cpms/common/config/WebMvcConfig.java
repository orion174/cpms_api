package com.cpms.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${editor.file.upload.path}")
    private String fileUploadPath;

    /* 네이버 스마트 에디터 파일 첨부 미리 보기 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/upload/editor/**")
                .addResourceLocations("file:" + fileUploadPath + "/");
    }
}
