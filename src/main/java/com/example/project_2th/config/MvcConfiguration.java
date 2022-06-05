package com.example.project_2th.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    // 파일 불러오기 위한 경로 설정
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/video/**")// 비디오 파일
                .addResourceLocations("file:///C:/user/projectVideo/");
        registry.addResourceHandler("/resultImage/**")// 자세교정 이미지
                .addResourceLocations("file:///C:/user/badImage/");
        registry.addResourceHandler("/image/**")
                .addResourceLocations("classpath:static/image/");
    }

}
