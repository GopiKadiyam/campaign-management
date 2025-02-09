package com.gk.campaign.exceptions;

import com.gk.campaign.utils.enums.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(InvalidRequestException ex, HttpServletRequest request) {
        GlobalExceptionRes errorResponse = GlobalExceptionRes.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(""+HttpStatus.UNAUTHORIZED.value())
                .path(request.getServletPath())
                .errorMsg(HttpStatus.UNAUTHORIZED.toString())
                .errorType(ErrorType.VALIDATION_ERROR.toString())
                .errors(ex.errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(InvalidRequestException ex, HttpServletRequest request) {
        GlobalExceptionRes errorResponse = GlobalExceptionRes.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(""+HttpStatus.BAD_REQUEST.value())
                .path(request.getServletPath())
                .errorMsg(HttpStatus.BAD_REQUEST.toString())
                .errorType(ErrorType.VALIDATION_ERROR.toString())
                .errors(ex.errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex, HttpServletRequest request) {
        GlobalExceptionRes errorResponse = GlobalExceptionRes.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(""+HttpStatus.BAD_REQUEST.value())
                .path(request.getServletPath())
                .errorMsg(HttpStatus.BAD_REQUEST.toString())
                .errorType(ErrorType.VALIDATION_ERROR.toString())
                .errors(ex.errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        GlobalExceptionRes errorResponse = GlobalExceptionRes.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(""+HttpStatus.NOT_FOUND.value())
                .path(request.getServletPath())
                .errorMsg(HttpStatus.NOT_FOUND.toString())
                .errorType(ErrorType.DATA_ERROR.toString())
                .errors(ex.errors)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    /**
     * Handle generic exceptions (500 Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGenericException(Exception ex,HttpServletRequest request) {
        GlobalExceptionRes errorResponse = GlobalExceptionRes.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(""+HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getServletPath())
                .errorMsg(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .errorType(ErrorType.DATA_ERROR.toString())
                .errors(Map.of("error","Internal Server Error"))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
