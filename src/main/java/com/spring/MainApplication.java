package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@EnableScheduling
@SpringBootApplication
public class MainApplication {

  public static void main (String[] args) {
    SpringApplication application = new SpringApplication(MainApplication.class);
    application.run(args);
  }

//  @Bean
//  public WebMvcConfigurer corsConfigurer () {
//    return new WebMvcConfigurer() {
//      @Override
//      public void addCorsMappings (CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET, POST, PATCH, PUT, DELETE, OPTIONS, HEAD")
//                .allowedHeaders("Access-Control-Allow-Origin," +
//                        "Access-Control-Allow-Headers," +
//                        "Access-Control-Allow-Methods," +
//                        "Accept,Authorization,Content-Type," +
//                        "Method,Origin,X-Forwarded-For,X-Real-IP"
//                )
//                .exposedHeaders("X-Get-Header");
//      }
//    };
//  }


  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("/**").allowedMethods("*").allowedHeaders("*");
      }
    };
  }
}
