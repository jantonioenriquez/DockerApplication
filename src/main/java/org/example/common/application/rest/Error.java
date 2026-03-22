package org.example.common.application.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {
    @JsonProperty("item")
    String item;
    @JsonProperty("message")
    String message;

    public Error(String item, String message) {
        this.item = item;
        this.message = message;
    }
}
