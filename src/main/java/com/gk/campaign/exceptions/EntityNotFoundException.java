package com.gk.campaign.exceptions;

import java.util.Map;

public class EntityNotFoundException extends RuntimeException {
    Map<String, String> errors;

    public EntityNotFoundException() {

    }

    public EntityNotFoundException(Map<String, String> errors) {
        this.errors = errors;
    }
}
