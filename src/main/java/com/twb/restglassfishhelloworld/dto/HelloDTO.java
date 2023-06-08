package com.twb.restglassfishhelloworld.dto;

public record HelloDTO(String message) {

    public String getMessage() {
        return message;
    }
}