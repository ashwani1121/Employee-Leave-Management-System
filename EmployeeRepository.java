package com.elms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elms.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailAndPassword(String email, String password);
    Optional<Employee> findByEmail(String email);
}
