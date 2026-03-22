package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.common.application.handler.ErrorDTO;
import org.example.common.application.handler.GlobalExceptionHandler;
import org.example.domain.exception.ProductDomainException;
import org.example.domain.exception.ProductNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Order(1)
@ControllerAdvice
public class ProductGlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {ProductDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ProductDomainException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage()).build();
    }


    @ResponseBody
    @ExceptionHandler(value = {ProductNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFoundException(ProductNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder().code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage()).build();
    }


}
