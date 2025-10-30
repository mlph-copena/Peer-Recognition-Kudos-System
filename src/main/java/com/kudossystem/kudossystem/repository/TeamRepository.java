package com.kudossystem.kudossystem.repository;

import com.kudossystem.kudossystem.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);

    List<Team> findByNameContainingIgnoreCase(String name);

    // ✅ Added reset query
    @Modifying
    @Transactional
    @Query("UPDATE Team t SET t.kudosCount = 0")
    void resetAllKudosCounts();
}