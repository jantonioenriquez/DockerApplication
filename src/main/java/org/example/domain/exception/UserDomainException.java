package org.example.domain.exception;

import lombok.Getter;
import org.example.common.application.handler.DomainException;

@Getter
public class UserDomainException extends DomainException {
    private Integer codeError;
    public UserDomainException(String message, Integer codeError) {
        super(message);
        this.codeError = codeError;
    }

    public UserDomainException(String message) {
        super(message);
    }
}
