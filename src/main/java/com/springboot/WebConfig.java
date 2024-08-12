package com.springboot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//  @Override
//  public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/**")
//        .allowedOriginPatterns("*") // 허용할 도메인
//        .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
//        .allowCredentials(true);
//  }
//}

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("*")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS", "DELETE")
        .allowedHeaders("Content-Type", "Authorization", "Accept")
        .allowCredentials(true)
        .maxAge(5000);
  }
}