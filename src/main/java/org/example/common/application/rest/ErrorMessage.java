package org.example.common.application.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessage {
    @JsonProperty("errors")
    List<Error> errors;
    @JsonProperty("type")
    String type;

    public ErrorMessage(String type) {
        this.type = type;
        this.errors = new ArrayList<>();
    }
}
