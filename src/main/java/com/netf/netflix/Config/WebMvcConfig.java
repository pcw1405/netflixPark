package com.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //  addResourceHandlers 메소드를 통해서 자신의 로컬 컴퓨터에 업로드한 파일을 찾을 위치를 설정
    @Value(("${uploadPath}"))
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/**") //가상으로 상위 폴더를 더 올려준다. //보안상으로 누구든 경로로 찾을수있기때문에 보안상으로 해준다.
                .addResourceLocations(uploadPath);
    }

}
