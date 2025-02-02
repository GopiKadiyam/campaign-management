package com.gk.campaign.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalExceptionRes {

    private String status;
    private String errorMsg;
    private String errorType;
    private String path;
    private String timestamp;
    private Map<String,String> errors;

}
