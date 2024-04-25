package com.example.cs_test_assignment.exceptions;

public class EntityNotUpdatedException extends RuntimeException {
    public EntityNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
