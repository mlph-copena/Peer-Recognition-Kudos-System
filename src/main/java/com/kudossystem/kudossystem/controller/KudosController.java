package com.kudossystem.kudossystem.controller;

import com.kudossystem.kudossystem.domain.Kudos;
import com.kudossystem.kudossystem.domain.KudosType;
import com.kudossystem.kudossystem.dto.KudosCommentDTO;
import com.kudossystem.kudossystem.dto.KudosLeaderboardEmployeeDto;
import com.kudossystem.kudossystem.dto.KudosLeaderboardTeamDto;
import com.kudossystem.kudossystem.service.KudosService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kudos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class KudosController {

    private final KudosService kudosService;


    // ----------------- Submit Kudos/Comment -----------------
    @PostMapping
    public Kudos submit(@RequestBody KudosRequest request) {
        KudosType type = request.getTypeSafe();

        return switch (type) {
            case kudos -> {
                if (request.getReceiverId() != null) {
                    yield kudosService.sendEmployeeKudos(
                            request.getSenderId(),
                            request.getReceiverId(),
                            request.getMessage(),
                            request.isAnonymous()
                    );
                } else if (request.getTeamId() != null) {
                    yield kudosService.sendTeamKudos(
                            request.getSenderId(),
                            request.getTeamId(),
                            request.getMessage(),
                            request.isAnonymous()
                    );
                } else {
                    throw new RuntimeException("Kudos must have receiverId or teamId");
                }
            }
            case comment -> {
                if (request.getReceiverId() == null && request.getTeamId() == null) {
                    throw new RuntimeException("Comment must have a target (employee or team)");
                }
                yield kudosService.leaveComment(
                        request.getSenderId(),
                        request.getReceiverId() != null ? request.getReceiverId() : request.getTeamId(),
                        request.getMessage(),
                        request.isAnonymous(),
                        request.getTeamId() != null
                );
            }
        };
    }

    // ----------------- Get Received Kudos -----------------
    @GetMapping("/received/{receiverId}")
    public List<Kudos> getReceivedKudos(@PathVariable Long receiverId) {
        return kudosService.getKudosByEmployee(receiverId, 7); // default last 7 days
    }

    @GetMapping
    public List<Kudos> getAllKudos() {
        return kudosService.getAllKudos();
    }

    // ----------------- Get Kudos/Comments by Employee -----------------
    @GetMapping("/employee/{employeeId}")
    public List<Kudos> getEmployeeKudos(
            @PathVariable Long employeeId,
            @RequestParam(defaultValue = "7") int days
    ) {
        return kudosService.getKudosByEmployee(employeeId, days);
    }

    // ----------------- Get Kudos/Comments by Team -----------------
    @GetMapping("/team/{teamId}")
    public List<Kudos> getTeamKudos(
            @PathVariable Long teamId,
            @RequestParam(defaultValue = "7") int days
    ) {
        return kudosService.getKudosByTeam(teamId, days);
    }

    // ----------------- Get Recent Kudos System-wide -----------------
    @GetMapping("/recent")
    public List<Kudos> getRecentKudos(@RequestParam(defaultValue = "10") int limit) {
        return kudosService.getRecentKudos(limit);
    }

    // ----------------- Search Kudos/Comments -----------------
    @GetMapping("/search")
    public List<Kudos> searchKudos(@RequestParam String query) {
        return kudosService.searchKudos(query);
    }

    // ----------------- Request DTO -----------------
    @Getter
    @Setter
    public static class KudosRequest {
        private Long senderId;
        private Long receiverId; // optional if team
        private Long teamId;     // optional if employee
        private String message;
        private boolean anonymous;
        private String type;

        public KudosType getTypeSafe() {
            if (type == null) throw new RuntimeException("Kudos type is required");
            return switch (type.toLowerCase()) {
                case "kudos" -> KudosType.kudos;
                case "comment" -> KudosType.comment;
                default -> throw new RuntimeException("Invalid type, must be 'kudos' or 'comment'");
            };
        }
    }

    @GetMapping("/all")
    public List<KudosCommentDTO> getAllKudosComments(@RequestParam String query) {
        return kudosService.findAllKudosComments(query);
    }

    // Top 5 employees
    @GetMapping("/employees")
    public List<KudosLeaderboardEmployeeDto> topEmployees(
            @RequestParam(required = false) String department) {
        return kudosService.getTopEmployeesByKudos(department);
    }

    // Top 5 teams
    @GetMapping("/teams")
    public List<KudosLeaderboardTeamDto> topTeams() {
        return kudosService.getTopTeamsByKudos();
    }
}