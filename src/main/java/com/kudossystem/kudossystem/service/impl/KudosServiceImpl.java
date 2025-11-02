package com.kudossystem.kudossystem.service.impl;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.domain.Kudos;
import com.kudossystem.kudossystem.domain.KudosType;
import com.kudossystem.kudossystem.domain.Team;
import com.kudossystem.kudossystem.dto.KudosCommentDTO;
import com.kudossystem.kudossystem.dto.KudosLeaderboardEmployeeDto;
import com.kudossystem.kudossystem.dto.KudosLeaderboardTeamDto;
import com.kudossystem.kudossystem.repository.EmployeeRepository;
import com.kudossystem.kudossystem.repository.KudosRepository;
import com.kudossystem.kudossystem.repository.TeamRepository;
import com.kudossystem.kudossystem.service.KudosService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KudosServiceImpl implements KudosService {

    private final KudosRepository kudosRepository;
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;

    // ----------------- Existing Methods -----------------
    @Override
    public Kudos sendEmployeeKudos(Long senderId, Long receiverId, String message, boolean anonymous) {
        Employee sender = employeeRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Employee receiver = employeeRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Kudos kudos = new Kudos();
        kudos.setSender(sender);
        kudos.setReceiver(receiver);
        kudos.setMessage(message);
        kudos.setAnonymous(anonymous);
        kudos.setType(KudosType.kudos);

        receiver.setKudosCount(receiver.getKudosCount() + 1);
        employeeRepository.save(receiver);

        return kudosRepository.save(kudos);
    }

    @Override
    public Kudos sendTeamKudos(Long senderId, Long teamId, String message, boolean anonymous) {
        Employee sender = employeeRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        LocalDate today = LocalDate.now();
        boolean exists = kudosRepository.existsBySenderAndTargetTeamAndDate(senderId, teamId, today);
        if (exists) throw new RuntimeException("You already sent kudos to this team today!");

        Kudos kudos = new Kudos();
        kudos.setSender(sender);
        kudos.setTargetTeam(team);
        kudos.setMessage(message);
        kudos.setAnonymous(anonymous);
        kudos.setType(KudosType.kudos);

        team.setKudosCount(team.getKudosCount() + 1);
        teamRepository.save(team);

        return kudosRepository.save(kudos);
    }

    @Override
    public Kudos leaveComment(Long senderId, Long targetId, String message, boolean anonymous, boolean isTeam) {
        Employee sender = employeeRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        LocalDate today = LocalDate.now();

        if (isTeam) {
            Team team = teamRepository.findById(targetId)
                    .orElseThrow(() -> new RuntimeException("Team not found"));

            boolean exists = kudosRepository.existsBySenderAndTargetTeamAndTypeAndDate(sender, team, KudosType.comment, today);
            if (exists) throw new RuntimeException("You already left a comment for this team today!");

            Kudos kudos = new Kudos();
            kudos.setSender(sender);
            kudos.setTargetTeam(team);
            kudos.setMessage(message);
            kudos.setAnonymous(anonymous);
            kudos.setType(KudosType.comment);

            return kudosRepository.save(kudos);

        } else {
            Employee receiver = employeeRepository.findById(targetId)
                    .orElseThrow(() -> new RuntimeException("Receiver not found"));

            boolean exists = kudosRepository.existsBySenderAndReceiverAndTypeAndDate(sender, receiver, KudosType.comment, today);
            if (exists) throw new RuntimeException("You already left a comment for this employee today!");

            Kudos kudos = new Kudos();
            kudos.setSender(sender);
            kudos.setReceiver(receiver);
            kudos.setMessage(message);
            kudos.setAnonymous(anonymous);
            kudos.setType(KudosType.comment);

            return kudosRepository.save(kudos);
        }
    }

    @Override
    public List<Kudos> findByReceiver(Employee receiver) {
        return kudosRepository.findByReceiver(receiver);
    }

    @Override
    public List<Kudos> getAllKudos() {
        return kudosRepository.findAll();
    }

    @Override
    public List<Kudos> findByTeam(Team team) {
        return kudosRepository.findByTargetTeam(team);
    }

    // ----------------- New Methods -----------------

    @Override
    public List<Kudos> getKudosByEmployee(Long employeeId, int pastDays) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDateTime fromDate = LocalDateTime.now().minusDays(pastDays);
        return kudosRepository.findByReceiverSince(employee, fromDate);
    }

    @Override
    public List<Kudos> getKudosByTeam(Long teamId, int pastDays) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        LocalDateTime fromDate = LocalDateTime.now().minusDays(pastDays);
        return kudosRepository.findByTeamSince(team, fromDate);
    }

    @Override
    public List<Kudos> getRecentKudos(int limit) {
        return kudosRepository.findRecentKudos(PageRequest.of(0, limit));
    }

    @Override
    public List<Kudos> searchKudos(String query) {
        return kudosRepository.searchByEmployeeOrTeamName(query);
    }

    @Override
    public List<KudosCommentDTO> findAllKudosComments(String query) {
        return List.of();
    }

    @Override
    public List<KudosLeaderboardEmployeeDto> getTopEmployeesByKudos(String department) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusDays(30);
        List<Object[]> results = department == null || department.isEmpty()
                ? kudosRepository.findTopEmployeesSince(oneMonthAgo)
                : kudosRepository.findTopEmployeesByDepartmentSince(department, oneMonthAgo);

        return results.stream()
                .limit(5) // ensure top 5
                .map(r -> new KudosLeaderboardEmployeeDto(
                        (String) r[0], // name
                        (String) r[1], // department
                        ((Number) r[2]).longValue() // comment count
                ))
                .collect(Collectors.toList());
    }

    public List<KudosLeaderboardTeamDto> getTopTeamsByKudos() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusDays(30);
        List<Object[]> results = kudosRepository.findTopTeamsSince(oneMonthAgo);
        return results.stream()
                .map(r -> new KudosLeaderboardTeamDto((String) r[0], ((Number) r[1]).longValue()))
                .collect(Collectors.toList());
    }

    // ✅ (Optional) Admin Reset Feature
    public void resetAllKudosCounts() {
        employeeRepository.resetAllKudosCounts();
        teamRepository.resetAllKudosCounts();
    }
}
