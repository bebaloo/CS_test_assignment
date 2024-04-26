package com.example.cs_test_assignment.rest.exception.handler;

import com.example.cs_test_assignment.exceptions.EntityNotCreatedException;
import com.example.cs_test_assignment.exceptions.EntityNotDeletedException;
import com.example.cs_test_assignment.exceptions.EntityNotFoundException;
import com.example.cs_test_assignment.exceptions.EntityNotUpdatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotCreatedException.class)
    public ResponseEntity<String> handleEntityNotCreatedException(EntityNotCreatedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotUpdatedException.class)
    public ResponseEntity<String> handleEntityNotUpdatedException(EntityNotUpdatedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotDeletedException.class)
    public ResponseEntity<String> handleEntityNotDeletedException(EntityNotDeletedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage() + "\n")
                .collect(Collectors.joining());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(DateTimeParseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
