package com.gk.campaign.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Component
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Allow frontend domain (specific origin)
        config.setAllowedOrigins(List.of("http://localhost:4200"));

        // ✅ Allow credentials (for cookies)
        config.setAllowCredentials(true);

        // ✅ Allow necessary headers and methods
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Apply configuration to all endpoints
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}