package com.elms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.elms.model.Employee;
import com.elms.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Employee emp) {
        if (emp.getEmail() == null || emp.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required."));
        }
        if (emp.getRole() == null || emp.getRole().isBlank()) {
            emp.setRole("EMPLOYEE");
        }
        if (employeeRepo.findByEmail(emp.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered."));
        }
        Employee saved = employeeRepo.save(emp);
        // Hide password in response
        saved.setPassword(null);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return employeeRepo.findByEmailAndPassword(email, password)
                .map(emp -> {
                    emp.setPassword(null); // do not expose password
                    return ResponseEntity.ok(emp);
                })
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Invalid email or password")));
    }
}
