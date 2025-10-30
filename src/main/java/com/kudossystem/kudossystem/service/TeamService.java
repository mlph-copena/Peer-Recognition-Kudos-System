package com.kudossystem.kudossystem.service;


import com.kudossystem.kudossystem.domain.Team;
import com.kudossystem.kudossystem.repository.TeamRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    public TeamService(TeamRepository teamRepository) { this.teamRepository = teamRepository; }

    public Team save(Team team) { return teamRepository.save(team); }

    public Optional<Team> findByName(String name) { return teamRepository.findByName(name); }
}