package com.example.cs_test_assignment.service.impl;

import com.example.cs_test_assignment.exceptions.EntityNotCreatedException;
import com.example.cs_test_assignment.exceptions.EntityNotDeletedException;
import com.example.cs_test_assignment.exceptions.EntityNotFoundException;
import com.example.cs_test_assignment.exceptions.EntityNotUpdatedException;
import com.example.cs_test_assignment.model.dto.BirthDateRangeDTO;
import com.example.cs_test_assignment.model.dto.RequestUserDTO;
import com.example.cs_test_assignment.model.dto.ResponseUserDTO;
import com.example.cs_test_assignment.model.entities.User;
import com.example.cs_test_assignment.repository.UserRepository;
import com.example.cs_test_assignment.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ResponseUserDTO create(RequestUserDTO requestUserDTO) {
        try {
            User user = modelMapper.map(requestUserDTO, User.class);
            return modelMapper.map(userRepository.save(user), ResponseUserDTO.class);
        } catch (RuntimeException e) {
            throw new EntityNotCreatedException(requestUserDTO + " not created", e);
        }
    }

    @Override
    public ResponseUserDTO update(RequestUserDTO requestUserDTO, Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("User with id: " + id + " not found"));

            modelMapper.map(requestUserDTO, user, "noMapId");

            return modelMapper.map(userRepository.save(user), ResponseUserDTO.class);
        } catch (RuntimeException e) {
            throw new EntityNotUpdatedException(requestUserDTO + " not updated", e);
        }
    }

    @Override
    public ResponseUserDTO updateFields(RequestUserDTO requestUserDTO, Long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("User with id: " + id + " not found"));

            modelMapper.map(requestUserDTO, user, "noMapNull");

            return modelMapper.map(userRepository.save(user), ResponseUserDTO.class);
        } catch (RuntimeException e) {
            throw new EntityNotUpdatedException(requestUserDTO + " not updated", e);
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
    public List<ResponseUserDTO> searchByBirthDateRange(BirthDateRangeDTO birthDateRangeDTO) {
        List<User> users = userRepository
                .findUsersByBirthDateBetween(
                        birthDateRangeDTO.getFromDate(),
                        birthDateRangeDTO.getToDate());

        return users.stream()
                .map(user -> modelMapper.map(user, ResponseUserDTO.class))
                .toList();
    }
}
