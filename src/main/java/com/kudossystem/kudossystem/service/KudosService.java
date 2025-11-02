package com.kudossystem.kudossystem.service;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.domain.Kudos;
import com.kudossystem.kudossystem.domain.Team;
import com.kudossystem.kudossystem.dto.KudosCommentDTO;
import com.kudossystem.kudossystem.dto.KudosLeaderboardEmployeeDto;
import com.kudossystem.kudossystem.dto.KudosLeaderboardTeamDto;

import java.util.List;

public interface KudosService {
    Kudos sendEmployeeKudos(Long senderId, Long receiverId, String message, boolean anonymous);
    Kudos sendTeamKudos(Long senderId, Long teamId, String message, boolean anonymous);
    Kudos leaveComment(Long senderId, Long targetId, String message, boolean anonymous, boolean isTeam);

    List<Kudos> findByReceiver(Employee receiver);
    List<Kudos> getAllKudos();
    List<Kudos> findByTeam(Team team);

    // ✅ New methods for history & search
    List<Kudos> getKudosByEmployee(Long employeeId, int pastDays);
    List<Kudos> getKudosByTeam(Long teamId, int pastDays);
    List<Kudos> getRecentKudos(int limit);
    List<Kudos> searchKudos(String query);

    List<KudosCommentDTO> findAllKudosComments(String query);
    List<KudosLeaderboardEmployeeDto> getTopEmployeesByKudos(String department);
    List<KudosLeaderboardTeamDto> getTopTeamsByKudos();



}

