package com.example.cs_test_assignment.service.impl;

import com.example.cs_test_assignment.exceptions.EntityNotCreatedException;
import com.example.cs_test_assignment.exceptions.EntityNotDeletedException;
import com.example.cs_test_assignment.exceptions.EntityNotFoundException;
import com.example.cs_test_assignment.exceptions.EntityNotUpdatedException;
import com.example.cs_test_assignment.model.dto.UserDTO;
import com.example.cs_test_assignment.model.entities.User;
import com.example.cs_test_assignment.repository.UserRepository;
import com.example.cs_test_assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO create(UserDTO userDTO) {
        try {
            User user = modelMapper.map(userDTO, User.class);
            return modelMapper.map(userRepository.save(user), UserDTO.class);
        } catch (RuntimeException e) {
            throw new EntityNotCreatedException(userDTO + " not created", e);
        }
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("User with id: " + id + " not found"));

            BeanUtils.copyProperties(userDTO, user);

            return modelMapper.map(userRepository.save(user), UserDTO.class);
        } catch (RuntimeException e) {
            throw new EntityNotUpdatedException(userDTO + " not updated", e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("User with id: " + id + " not found"));
            userRepository.delete(user);
        } catch (RuntimeException e) {
            throw new EntityNotDeletedException("User with id:" + id + " not deleted", e);
        }
    }

    @Override
    public List<UserDTO> searchByBirthDateRange(LocalDate fromDate, LocalDate toDate) {
        List<User> users = userRepository.findUsersByBirthDateBetween(fromDate, toDate);
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }
}
