package com.kudossystem.kudossystem.controller;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.repository.TeamRepository;
import com.kudossystem.kudossystem.service.KudosService;
import com.kudossystem.kudossystem.repository.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class DashboardController {

    private final EmployeeRepository employeeRepository;
    private final KudosService kudosService;
    private  final TeamRepository teamRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal, HttpSession session) {
        Employee employee = employeeRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        String role = (String) session.getAttribute("role");
        if ("admin".equalsIgnoreCase(role)) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("employee", employee);
        model.addAttribute("receivedKudos", kudosService.findByReceiver(employee));
        model.addAttribute("allEmployees", employeeRepository.findAll());
        model.addAttribute("allTeams", teamRepository.findAll());

        return "dashboard";
    }
}
