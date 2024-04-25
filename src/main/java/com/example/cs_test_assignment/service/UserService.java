package com.example.cs_test_assignment.service;

import com.example.cs_test_assignment.model.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDTO create(UserDTO userDTO);
    UserDTO update(UserDTO userDTO, Long id);
    void delete(Long id);
    List<UserDTO> searchByBirthDateRange(LocalDate fromDate, LocalDate toDate);
}