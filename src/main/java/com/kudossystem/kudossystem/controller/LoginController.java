package com.kudossystem.kudossystem.controller;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final EmployeeService employeeService;

    @GetMapping("/login")
    public String login() {
        return "login"; // maps to login.html
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        Optional<Employee> employeeOpt = employeeService.findByEmail(email.trim());

        if (employeeOpt.isEmpty()) {
            model.addAttribute("error", "No account found with this email.");
            return "login";
        }

        Employee employee = employeeOpt.get();

        // DEBUG LOGGING
        System.out.println("=== Login Debug ===");
        System.out.printf("Email entered: %s%n", email);
        System.out.printf("Password entered: %s%n", password);
        System.out.printf("Password in DB: %s%n", employee.getPassword());
        System.out.printf("Department raw: '%s'%n", employee.getDepartment());
        System.out.println("===================");

        if (!password.equals(employee.getPassword())) {
            model.addAttribute("error", "Incorrect password.");
            return "login";
        }

        // Save user info in session
        String dept = employee.getDepartment() != null ? employee.getDepartment().trim().toLowerCase() : "";
        session.setAttribute("role", dept);
        session.setAttribute("user", employee);

        // Redirect sa universal dashboard
        return "redirect:/dashboard";
    }
}
