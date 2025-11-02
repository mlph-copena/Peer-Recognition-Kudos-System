package com.kudossystem.kudossystem.controller;

import org.springframework.ui.Model;
import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.repository.EmployeeRepository;
import com.kudossystem.kudossystem.repository.TeamRepository;
import com.kudossystem.kudossystem.service.KudosService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class DashboardRedirectController {

    private final EmployeeRepository employeeRepository;
    private final KudosService kudosService;
    private final TeamRepository teamRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (employee == null || role == null) {
            return "redirect:/login";
        }

        if ("admin".equalsIgnoreCase(role)) {
            return "redirect:/admin/dashboard";
        } else if ("employee".equalsIgnoreCase(role)) {
            model.addAttribute("employee", employee);
            model.addAttribute("receivedKudos", kudosService.findByReceiver(employee));
            model.addAttribute("allEmployees", employeeRepository.findAll());
            model.addAttribute("allTeams", teamRepository.findAll());
            return "dashboard";
        } else {
            session.invalidate();
            return "redirect:/login";
        }
    }
}