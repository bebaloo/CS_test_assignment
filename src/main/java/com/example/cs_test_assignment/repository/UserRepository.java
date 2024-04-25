package com.example.cs_test_assignment.repository;

import com.example.cs_test_assignment.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByBirthDateBetween(LocalDate fromDate, LocalDate toDate);
}
