package org.example.domain.exception;

import lombok.Getter;
import org.example.common.application.handler.DomainException;

@Getter
public class ProductDomainException extends DomainException {
    private Integer codeError;
    public ProductDomainException(String message, Integer codeError) {
        super(message);
        this.codeError = codeError;
    }

    public ProductDomainException(String message) {
        super(message);
    }
}