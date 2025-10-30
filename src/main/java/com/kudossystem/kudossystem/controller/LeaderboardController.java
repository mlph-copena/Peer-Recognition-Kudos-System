package com.kudossystem.kudossystem.controller;

import com.kudossystem.kudossystem.repository.EmployeeRepository;
import com.kudossystem.kudossystem.repository.TeamRepository;
import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LeaderboardController {

    private final EmployeeRepository employeeRepo;
    private final TeamRepository teamRepo;

    @GetMapping("/leaderboard")
    public String leaderboardPage() {
        return "leaderboard"; // the Thymeleaf template file
    }
}
