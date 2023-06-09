package com.twb.restglassfishhelloworld.dto;

public class CreateBookDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateBookDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
