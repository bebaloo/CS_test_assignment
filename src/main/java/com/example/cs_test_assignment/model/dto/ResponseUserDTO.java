package com.example.cs_test_assignment.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseUserDTO {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private LocalDate birthDate;
        private String address;
        private String phoneNumber;
}
