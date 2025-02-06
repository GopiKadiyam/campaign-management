package com.gk.campaign.exceptions.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.campaign.utils.enums.ErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_FORBIDDEN);
        body.put("errorMsg", accessDeniedException.getMessage());
        body.put("errorType", ErrorType.AUTHENTICATION_ERROR.toString());
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("path", request.getServletPath());
        body.put("errors",Map.of("error","You do not have permission to access this resource."));

//        final ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getOutputStream(), body);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
        response.getOutputStream().flush(); // Ensure the JSON is fully written
        response.getOutputStream().close();

    }

}