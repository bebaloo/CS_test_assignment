package com.example.cs_test_assignment.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotDeletedException extends RuntimeException {
    public EntityNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
