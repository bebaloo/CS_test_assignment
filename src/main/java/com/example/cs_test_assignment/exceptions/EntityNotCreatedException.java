package com.example.cs_test_assignment.exceptions;

public class EntityNotCreatedException extends RuntimeException {
    public EntityNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
