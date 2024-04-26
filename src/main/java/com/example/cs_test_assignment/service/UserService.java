package com.example.cs_test_assignment.service;

import com.example.cs_test_assignment.model.dto.BirthDateRangeDTO;
import com.example.cs_test_assignment.model.dto.RequestUserDTO;
import com.example.cs_test_assignment.model.dto.ResponseUserDTO;

import java.util.List;

public interface UserService {
    ResponseUserDTO create(RequestUserDTO requestUserDTO);

    ResponseUserDTO update(RequestUserDTO requestUserDTO, Long id);

    ResponseUserDTO updateFields(RequestUserDTO requestUserDTO, Long id);

    void delete(Long id);

    List<ResponseUserDTO> searchByBirthDateRange(BirthDateRangeDTO birthDateRangeDTO);
}