package com.twb.restglassfishhelloworld.dto;

public class HelloDTO {
    private final String message;

    public HelloDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}