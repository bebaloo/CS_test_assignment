package com.example.cs_test_assignment.model.dto;

import com.example.cs_test_assignment.validation.annotation.Adult;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestUserDTO {
    @Email
    private String email;
    private String firstName;
    private String lastName;
    @Past
    @Adult
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
