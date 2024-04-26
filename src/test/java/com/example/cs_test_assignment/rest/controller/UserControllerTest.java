package com.example.cs_test_assignment.rest.controller;

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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void testCreate_givenInvalidBirthDateUser_shouldReturnException_status400() throws Exception {
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
                .andExpect(content().string(containsString("User must be at least 18 years old")));
    }
    @Test
    void testCreate_givenInvalidEmailUser_shouldReturnException_status400() throws Exception {
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
                .andExpect(content().string(containsString("email: must be a well-formed email address")));
    }
    @Test
    void testUpdate() {

    }

    @Test
    void updateFields() {
    }

    @Test
    void delete() {
    }

    @Test
    void searchByBirthDateRange() {
    }
}