package org.example.domain.exception;


import org.example.common.application.handler.DomainException;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
