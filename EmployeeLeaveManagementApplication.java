package com.elms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.elms.model.Employee;
import com.elms.repository.EmployeeRepository;

@SpringBootApplication
public class EmployeeLeaveManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeLeaveManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(EmployeeRepository repo) {
        return args -> {
            // Seed a default EMPLOYEE
            repo.findByEmail("emp1@example.com").orElseGet(() -> {
                Employee e = new Employee();
                e.setName("Employee One");
                e.setEmail("emp1@example.com");
                e.setPassword("password");
                e.setRole("EMPLOYEE");
                return repo.save(e);
            });
            // Seed a default MANAGER
            repo.findByEmail("mgr1@example.com").orElseGet(() -> {
                Employee m = new Employee();
                m.setName("Manager One");
                m.setEmail("mgr1@example.com");
                m.setPassword("password");
                m.setRole("MANAGER");
                return repo.save(m);
            });
        };
    }
}
