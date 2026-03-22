package org.example.domain.exception;

import org.example.common.application.handler.DomainException;

public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
