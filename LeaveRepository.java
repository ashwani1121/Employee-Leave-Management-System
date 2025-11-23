package com.elms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elms.model.LeaveRequest;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeId(Long employeeId);
}
