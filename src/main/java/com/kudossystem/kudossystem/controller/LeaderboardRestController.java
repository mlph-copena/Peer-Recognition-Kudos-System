//package com.kudossystem.kudossystem.controller;
//
//import com.kudossystem.kudossystem.dto.DTO.EmployeeLeaderboardDto;
//import com.kudossystem.kudossystem.dto.DTO.TeamLeaderboardDto;
//import com.kudossystem.kudossystem.repository.EmployeeRepository;
//import com.kudossystem.kudossystem.repository.TeamRepository;
//import com.kudossystem.kudossystem.domain.Employee;
//import com.kudossystem.kudossystem.domain.Team;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequiredArgsConstructor
//public class LeaderboardRestController {
//
//    private final EmployeeRepository employeeRepo;
//    private final TeamRepository teamRepo;
//
//    // Top 5 employees by kudos received (optional filter by department)
//    @GetMapping("/api/kudos/leaderboard/employees")
//    public List<EmployeeLeaderboardDto> topEmployees(@RequestParam(required = false) String department) {
//        return employeeRepo.findAll().stream()
//                .filter(e -> department == null || e.getDepartment().equalsIgnoreCase(department))
//                .sorted((e1, e2) -> Integer.compare(e2.getKudosCount(), e1.getKudosCount()))
//                .limit(5)
//                .map(e -> new EmployeeLeaderboardDto(e.getId(), e.getName(), e.getKudosCount()))
//                .collect(Collectors.toList());
//    }
//
//    // Top 5 teams by kudos received (optional filter by team ID)
//    @GetMapping("/api/kudos/leaderboard/teams")
//    public List<TeamLeaderboardDto> topTeams(@RequestParam(required = false) Long teamId) {
//        return teamRepo.findAll().stream()
//                .filter(t -> teamId == null || t.getId().equals(teamId))
//                .sorted((t1, t2) -> Integer.compare(t2.getKudosCount(), t1.getKudosCount()))
//                .limit(5)
//                .map(t -> new TeamLeaderboardDto(t.getId(), t.getName(), t.getKudosCount()))
//                .collect(Collectors.toList());
//    }
//}
