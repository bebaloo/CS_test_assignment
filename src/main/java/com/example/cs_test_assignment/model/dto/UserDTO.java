package com.example.cs_test_assignment.model.dto;

import com.example.cs_test_assignment.validation.annotation.Adult;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record UserDTO(
        @Email
        String email,
        String firstName,
        String lastname,
        @Past
        @Adult
        LocalDate birthDate,
        String address,
        String phoneNumber) {
}
