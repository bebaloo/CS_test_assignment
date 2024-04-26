package com.example.cs_test_assignment.rest.controller;

import com.example.cs_test_assignment.exceptions.EntityNotDeletedException;
import com.example.cs_test_assignment.exceptions.EntityNotFoundException;
import com.example.cs_test_assignment.exceptions.EntityNotUpdatedException;
import com.example.cs_test_assignment.model.dto.BirthDateRangeDTO;
import com.example.cs_test_assignment.model.dto.RequestUserDTO;
import com.example.cs_test_assignment.model.dto.ResponseUserDTO;
import com.example.cs_test_assignment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private RequestUserDTO requestUserDTO;
    private ResponseUserDTO responseUserDTO;

    @BeforeEach
    void setUp() {
        requestUserDTO = new RequestUserDTO();
        requestUserDTO.setEmail("example@example.com");
        requestUserDTO.setFirstName("John");
        requestUserDTO.setLastName("Doe");
        requestUserDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        requestUserDTO.setAddress("123 Main St");
        requestUserDTO.setPhoneNumber("+38-(099)-992-05-92");

        responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setId(1L);
        responseUserDTO.setEmail("example@example.com");
        responseUserDTO.setFirstName("John");
        responseUserDTO.setLastName("Doe");
        responseUserDTO.setBirthDate(LocalDate.of(1990, 5, 15));
        responseUserDTO.setAddress("123 Main St");
        responseUserDTO.setPhoneNumber("+38-(099)-992-05-92");

    }

    @Test
    void testCreate_givenValidUser_shouldReturnCreatedUser_status201() throws Exception {
        when(userService.create(any(RequestUserDTO.class))).thenReturn(responseUserDTO);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "example@example.com",
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "birthDate": "1990-05-15",
                                  "address": "123 Main St",
                                  "phoneNumber": "+38-(099)-992-05-92"
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .value(responseUserDTO.getId()))
                .andExpect(jsonPath("$.email")
                        .value(responseUserDTO.getEmail()))
                .andExpect(jsonPath("$.firstName")
                        .value(responseUserDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName")
                        .value(responseUserDTO.getLastName()))
                .andExpect(jsonPath("$.birthDate")
                        .value(responseUserDTO.getBirthDate().toString()))
                .andExpect(jsonPath("$.address")
                        .value(responseUserDTO.getAddress()))
                .andExpect(jsonPath("$.phoneNumber")
                        .value(responseUserDTO.getPhoneNumber()));

        verify(userService).create(requestUserDTO);
    }

    @Test
    void testCreate_givenInvalidBirthDateUser_shouldThrowException_status400() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "example@example.com",
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "birthDate": "2010-05-15",
                                  "address": "123 Main St",
                                  "phoneNumber": "+38-(099)-992-05-92"
                                }"""))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .string(containsString("User must be at least 18 years old")));
    }

    @Test
    void testCreate_givenInvalidEmailUser_shouldThrowException_status400() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "example",
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "birthDate": "2000-05-15",
                                  "address": "123 Main St",
                                  "phoneNumber": "+38-(099)-992-05-92"
                                }"""))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .string(containsString("email: must be a well-formed email address")));
    }

    @Test
    void testUpdate_givenUserAndId_shouldReturnUpdatedUser_status200() throws Exception {
        when(userService.update(any(RequestUserDTO.class), any(Long.class))).thenReturn(responseUserDTO);

        mockMvc.perform(put("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "example@example.com",
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "birthDate": "1990-05-15",
                                  "address": "123 Main St",
                                  "phoneNumber": "+38-(099)-992-05-92"
                                }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(responseUserDTO.getId()))
                .andExpect(jsonPath("$.email")
                        .value(responseUserDTO.getEmail()))
                .andExpect(jsonPath("$.firstName")
                        .value(responseUserDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName")
                        .value(responseUserDTO.getLastName()))
                .andExpect(jsonPath("$.birthDate")
                        .value(responseUserDTO.getBirthDate().toString()))
                .andExpect(jsonPath("$.address")
                        .value(responseUserDTO.getAddress()))
                .andExpect(jsonPath("$.phoneNumber")
                        .value(responseUserDTO.getPhoneNumber()));

        verify(userService).update(requestUserDTO, 1L);
    }

    @Test
    void testUpdate_givenUserAndInvalidId_shouldThrowException_status400() throws Exception {
        doThrow(new EntityNotUpdatedException("User not updated", new EntityNotFoundException("User not found")))
                .when(userService).update(any(), any());

        mockMvc.perform(put("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "example@example.com",
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "birthDate": "1990-05-15",
                                  "address": "123 Main St",
                                  "phoneNumber": "+38-(099)-992-05-92"
                                }"""))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("User not updated")));
    }

    @Test
    void testUpdateFields_givenUserAndId_shouldReturnUpdatedUser_status200() throws Exception {
        when(userService.updateFields(requestUserDTO, 1L)).thenReturn(responseUserDTO);

        mockMvc.perform(patch("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "example@example.com",
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "birthDate": "1990-05-15",
                                  "address": "123 Main St",
                                  "phoneNumber": "+38-(099)-992-05-92"
                                }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(responseUserDTO.getId()))
                .andExpect(jsonPath("$.email")
                        .value(responseUserDTO.getEmail()))
                .andExpect(jsonPath("$.firstName")
                        .value(responseUserDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName")
                        .value(responseUserDTO.getLastName()))
                .andExpect(jsonPath("$.birthDate")
                        .value(responseUserDTO.getBirthDate().toString()))
                .andExpect(jsonPath("$.address")
                        .value(responseUserDTO.getAddress()))
                .andExpect(jsonPath("$.phoneNumber")
                        .value(responseUserDTO.getPhoneNumber()));

        verify(userService).updateFields(requestUserDTO, 1L);
    }

    @Test
    void testUpdateFields_givenUserAndInvalidId_shouldThrowException_status400() throws Exception {
        doThrow(new EntityNotUpdatedException("User not updated", new EntityNotFoundException("User not found")))
                .when(userService).updateFields(any(), any());

        mockMvc.perform(patch("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "example@example.com",
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "birthDate": "1990-05-15",
                                  "address": "123 Main St",
                                  "phoneNumber": "+38-(099)-992-05-92"
                                }"""))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("User not updated")));
    }

    @Test
    void testDelete_givenValidId_status204() throws Exception {
        doNothing().when(userService).delete(any(Long.class));

        mockMvc.perform(delete("/api/v1/users/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userService).delete(1L);
    }

    @Test
    void testDelete_givenInvalidId_shouldThrowException_status400() throws Exception {
        doThrow(new EntityNotDeletedException("User not deleted", new EntityNotFoundException("User not found")))
                .when(userService).delete(any(Long.class));

        mockMvc.perform(delete("/api/v1/users/{id}", 1L))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("User not deleted")));

        verify(userService).delete(1L);
    }

    @Test
    void testSearchByBirthDateRange_givenByBirthDateRange_returnUsers_status200() throws Exception {
        ResponseUserDTO responseUserDTO1 = new ResponseUserDTO();
        responseUserDTO1.setId(2L);
        responseUserDTO1.setBirthDate(LocalDate.of(2000, 12, 12));

        List<ResponseUserDTO> users = new ArrayList<>();
        users.add(responseUserDTO);
        users.add(responseUserDTO1);

        when(userService.searchByBirthDateRange(any(BirthDateRangeDTO.class))).thenReturn(users);

        mockMvc.perform(get("/api/v1/users/birth-date/range")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "fromDate" : "1980-10-10",
                        "toDate" : "2003-10-10"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id")
                        .value(responseUserDTO.getId()))
                .andExpect(jsonPath("$.[0].birthDate")
                        .value(responseUserDTO.getBirthDate().toString()))
                .andExpect(jsonPath("$.[1].id")
                        .value(responseUserDTO1.getId()))
                .andExpect(jsonPath("$.[1].birthDate")
                        .value(responseUserDTO1.getBirthDate().toString()));

        verify(userService).searchByBirthDateRange(any(BirthDateRangeDTO.class));
    }
}