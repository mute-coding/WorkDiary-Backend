package com.rainey.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500",
                		 "https://mute-coding.github.io", // GitHub Page
                         "https://phylis-nonpresentational-gussie.ngrok-free.dev ") // ngrok)
                .allowedMethods("GET", "POST", "PUT", "DELETE","PATCH")
                .allowedHeaders("*");
    }
}
