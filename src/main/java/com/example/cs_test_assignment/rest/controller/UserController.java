package com.example.cs_test_assignment.rest.controller;

import com.example.cs_test_assignment.model.dto.BirthDateRangeDTO;
import com.example.cs_test_assignment.model.dto.RequestUserDTO;
import com.example.cs_test_assignment.model.dto.ResponseUserDTO;
import com.example.cs_test_assignment.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUserDTO> create(@Valid @RequestBody RequestUserDTO requestUserDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(requestUserDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> update(
            @PathVariable Long id, @Valid @RequestBody RequestUserDTO requestUserDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(requestUserDTO, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> updateFields(
            @PathVariable Long id, @Valid @RequestBody RequestUserDTO requestUserDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateFields(requestUserDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/birth-date/range")
    public ResponseEntity<List<ResponseUserDTO>> searchByBirthDateRange(
            @RequestBody BirthDateRangeDTO birthDateRangeDTO) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(userService.searchByBirthDateRange(birthDateRangeDTO));
    }
}
