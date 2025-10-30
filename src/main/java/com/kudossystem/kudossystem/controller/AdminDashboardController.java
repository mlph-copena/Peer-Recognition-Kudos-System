package com.kudossystem.kudossystem.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminDashboardController {

    @GetMapping("/dashboard/admin")
    public String adminDashboard(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"admin".equalsIgnoreCase(role)) {
            return "redirect:/login";
        }

        return "admin/dashboard"; // create this template
    }
}
