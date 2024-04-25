package com.example.cs_test_assignment.model.dto;

import java.time.LocalDate;

public record UserDTO(String email,
                      String firstName,
                      String lastname,
                      LocalDate birthDate,
                      String address,
                      String phoneNumber) {
}
