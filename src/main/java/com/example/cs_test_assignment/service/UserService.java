package com.example.cs_test_assignment.service;

import com.example.cs_test_assignment.model.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserService {
    UserDTO create(UserDTO userDTO);
    UserDTO update(UserDTO userDTO);
    UserDTO updateFields(Map<String, Object> updates);
    void delete(Long id);
    List<UserDTO> searchByBirthDateRange(LocalDate fromDate, LocalDate toDate);
}