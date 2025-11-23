package com.elms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.elms.model.LeaveRequest;
import com.elms.repository.LeaveRepository;

@RestController
@RequestMapping("/api/leaves")
@CrossOrigin(origins = "*")
public class LeaveController {

    @Autowired
    private LeaveRepository leaveRepo;

    @PostMapping("/apply")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequest leave) {
        if (leave.getEmployee() == null || leave.getEmployee().getId() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Employee with valid ID is required."));
        }
        if (leave.getStartDate() == null || leave.getEndDate() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Start and End dates are required."));
        }
        LeaveRequest saved = leaveRepo.save(leave);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/all")
    public List<LeaveRequest> getAllLeaves() {
        return leaveRepo.findAll();
    }

    @GetMapping("/employee/{employeeId}")
    public List<LeaveRequest> getByEmployee(@PathVariable Long employeeId) {
        return leaveRepo.findByEmployeeId(employeeId);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveLeave(@PathVariable Long id) {
        return leaveRepo.findById(id)
            .map(lr -> {
                lr.setStatus("APPROVED");
                return ResponseEntity.ok(leaveRepo.save(lr));
            })
            .orElse(ResponseEntity.status(404).body(Map.of("error", "Leave not found")));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectLeave(@PathVariable Long id) {
        return leaveRepo.findById(id)
            .map(lr -> {
                lr.setStatus("REJECTED");
                return ResponseEntity.ok(leaveRepo.save(lr));
            })
            .orElse(ResponseEntity.status(404).body(Map.of("error", "Leave not found")));
    }
}
