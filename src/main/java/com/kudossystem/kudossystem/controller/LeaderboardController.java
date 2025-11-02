package com.kudossystem.kudossystem.controller;

import com.kudossystem.kudossystem.repository.EmployeeRepository;
import com.kudossystem.kudossystem.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


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
