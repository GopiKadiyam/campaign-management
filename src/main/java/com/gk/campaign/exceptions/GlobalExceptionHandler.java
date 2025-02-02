package com.gk.campaign.exceptions;

import com.gk.campaign.utils.enums.ErrorType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex, HttpServletRequest request) {
        GlobalExceptionRes errorResponse = GlobalExceptionRes.builder()
                .timestamp(new Date().toString())
                .status(""+HttpStatus.BAD_REQUEST.value())
                .path(request.getServletPath())
                .errorMsg(HttpStatus.BAD_REQUEST.toString())
                .errorType(ErrorType.VALIDATION_ERROR.toString())
                .errors(ex.errors)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
