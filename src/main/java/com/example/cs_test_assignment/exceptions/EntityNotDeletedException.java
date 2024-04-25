package com.example.cs_test_assignment.exceptions;

public class EntityNotDeletedException extends RuntimeException {
    public EntityNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
