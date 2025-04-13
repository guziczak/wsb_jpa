package com.jpacourse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:63342")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Zezwól na dostęp z localhost:63342
        config.addAllowedOrigin("http://localhost:63342");

        // Zezwól na wszystkie metody (GET, POST, itp.)
        config.addAllowedMethod("*");

        // Zezwól na wszystkie nagłówki
        config.addAllowedHeader("*");

        // Zezwól na przesyłanie ciasteczek (jeśli używasz uwierzytelniania)
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}