package com.gk.campaign.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfigurationSource source = corsConfigurationSource();
        return new CorsFilter(source);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Allow requests from frontend (Angular running on localhost:4200)
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));

        // Allow common HTTP methods
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow all headers
        corsConfig.setAllowedHeaders(List.of("*"));

        // Allow credentials (needed for cookies, including HttpOnly JWT tokens)
        corsConfig.setAllowCredentials(true);

        // Expose headers (if needed)
        corsConfig.setExposedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }
}