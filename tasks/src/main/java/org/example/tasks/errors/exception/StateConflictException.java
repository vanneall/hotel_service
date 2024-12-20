package org.example.tasks.errors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StateConflictException extends RuntimeException {
    public StateConflictException(String message) {
        super(message);
    }
}
