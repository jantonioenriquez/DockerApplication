package org.example.common.application.rest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ApplicationController {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorMessage handleConstraintViolation(HttpServletRequest httpServletRequest, ConstraintViolationException e) {
        Set<MediaType> mediaTypes = new HashSet<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpServletRequest.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE, mediaTypes);
        log.error("Illegal arguments received.", e);
        String errorMessage = e.getMessage();
        String[] errorMessageArray = errorMessage.split(",", 0);
        ErrorMessage errorMessageResponse = new ErrorMessage("Illegal arguments");
        for (int i = 0; i < errorMessageArray.length; i++) {
            String[] errorMessageItem = errorMessageArray[i].split(":", 0);
            Error error = new Error(errorMessageItem[0].substring(errorMessageItem[0].lastIndexOf(".") + 1),
                    errorMessageItem[1].trim());
            errorMessageResponse.errors.add(error);
        }
        return errorMessageResponse;
    }

    public Logger loggerService() {
        return new Logger();
    }
}
