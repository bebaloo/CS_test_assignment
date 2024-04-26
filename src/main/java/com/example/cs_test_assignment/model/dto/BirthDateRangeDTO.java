package com.example.cs_test_assignment.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BirthDateRangeDTO {
    private LocalDate fromDate;
    private LocalDate toDate;
}
