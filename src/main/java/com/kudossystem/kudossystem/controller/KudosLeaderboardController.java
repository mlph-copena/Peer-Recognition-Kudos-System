package com.kudossystem.kudossystem.controller;

import com.kudossystem.kudossystem.dto.KudosLeaderboardEmployeeDto;
import com.kudossystem.kudossystem.dto.KudosLeaderboardTeamDto;
import com.kudossystem.kudossystem.service.KudosService;
import com.kudossystem.kudossystem.service.impl.KudosServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kudos/leaderboard")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class KudosLeaderboardController {

    private final KudosService kudosService;

    // ✅ Top 5 employees by kudos_comment (past month)
    @GetMapping("/employees")
    public List<KudosLeaderboardEmployeeDto> getTopEmployees(@RequestParam(required = false) String department) {
        return kudosService.getTopEmployeesByKudos(department);
    }

    // ✅ Top 5 teams by kudos (past month)
    @GetMapping("/teams")
    public List<KudosLeaderboardTeamDto> getTopTeams() {
        return kudosService.getTopTeamsByKudos();
    }

    // ✅ Admin Reset Kudos Counts (optional)
    @PostMapping("/reset")
    public ResponseEntity<String> resetKudosCounts() {
        ((KudosServiceImpl) kudosService).resetAllKudosCounts();
        return ResponseEntity.ok("✅ All kudos and comment counts have been reset.");
    }
}
