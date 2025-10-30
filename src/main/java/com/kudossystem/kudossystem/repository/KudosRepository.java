package com.kudossystem.kudossystem.repository;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.domain.Kudos;
import com.kudossystem.kudossystem.domain.KudosType;
import com.kudossystem.kudossystem.domain.Team;
import com.kudossystem.kudossystem.dto.KudosCommentDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KudosRepository extends JpaRepository<Kudos, Long> {
    List<Kudos> findByReceiver(Employee receiver);
    List<Kudos> findBySender(Employee sender);
    List<Kudos> findByTargetTeam(Team team);

    // Check duplicate kudos for team
    @Query("SELECT CASE WHEN COUNT(k) > 0 THEN true ELSE false END " +
            "FROM Kudos k WHERE k.sender.id = :senderId " +
            "AND k.targetTeam.id = :teamId " +
            "AND DATE(k.createdAt) = :date")
    boolean existsBySenderAndTargetTeamAndDate(@Param("senderId") Long senderId,
                                               @Param("teamId") Long teamId,
                                               @Param("date") LocalDate date);

    // Check if a comment already exists for an employee today
    @Query("SELECT CASE WHEN COUNT(k) > 0 THEN true ELSE false END " +
            "FROM Kudos k WHERE k.sender = :sender " +
            "AND k.receiver = :receiver " +
            "AND k.type = :type " +
            "AND DATE(k.createdAt) = :date")
    boolean existsBySenderAndReceiverAndTypeAndDate(@Param("sender") Employee sender,
                                                    @Param("receiver") Employee receiver,
                                                    @Param("type") KudosType type,
                                                    @Param("date") LocalDate date);

    // Check if a comment already exists for a team today
    @Query("SELECT CASE WHEN COUNT(k) > 0 THEN true ELSE false END " +
            "FROM Kudos k WHERE k.sender = :sender " +
            "AND k.targetTeam = :team " +
            "AND k.type = :type " +
            "AND DATE(k.createdAt) = :date")
    boolean existsBySenderAndTargetTeamAndTypeAndDate(@Param("sender") Employee sender,
                                                      @Param("team") Team team,
                                                      @Param("type") KudosType type,
                                                      @Param("date") LocalDate date);

    @Query("SELECT k FROM Kudos k WHERE LOWER(k.receiver.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY k.createdAt DESC")
    List<Kudos> findByReceiverNameContaining(@Param("query") String query);

    @Query("SELECT k FROM Kudos k WHERE LOWER(k.targetTeam.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY k.createdAt DESC")
    List<Kudos> findByTeamNameContaining(@Param("query") String query);

    @Query("SELECT k FROM Kudos k ORDER BY k.createdAt DESC")
    List<Kudos> findRecentKudos(Pageable pageable);

    // Get kudos for an employee since a certain date
    @Query("SELECT k FROM Kudos k WHERE k.receiver = :employee AND k.createdAt >= :fromDate ORDER BY k.createdAt DESC")
    List<Kudos> findByReceiverSince(@Param("employee") Employee employee,
                                    @Param("fromDate") LocalDateTime fromDate);

    // Get kudos for a team since a certain date
    @Query("SELECT k FROM Kudos k WHERE k.targetTeam = :team AND k.createdAt >= :fromDate ORDER BY k.createdAt DESC")
    List<Kudos> findByTeamSince(@Param("team") Team team,
                                @Param("fromDate") LocalDateTime fromDate);

    @Query("SELECT k FROM Kudos k " +
            "WHERE LOWER(k.receiver.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(k.targetTeam.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY k.createdAt DESC")
    List<Kudos> searchByEmployeeOrTeamName(@Param("query") String query);

    @Query("SELECT new com.kudossystem.kudossystem.dto.KudosCommentDTO(" +
            "k.id, " +
            "CASE WHEN k.anonymous = true THEN 'Anonymous' ELSE k.sender.name END, " +
            "k.receiver.name, " +
            "k.targetTeam.name, " +
            "k.message, " +
            "k.type, " +
            "k.anonymous, " +
            "k.createdAt) " +
            "FROM KudosComment k ORDER BY k.createdAt DESC")
    List<KudosCommentDTO> findAllKudosComments();


    // top 5 employees (past month) based on comments
    @Query("""
        SELECT e.name, e.department, COUNT(k) as commentCount
        FROM Kudos k
        JOIN k.receiver e
        WHERE k.createdAt >= :since
        AND k.type = 'comment'
        GROUP BY e.name, e.department
        ORDER BY COUNT(k) DESC
    """)
        List<Object[]> findTopEmployeesSince(@Param("since") LocalDateTime since);

    // top 5 employees (filtered by department) based on comments
    @Query("""
        SELECT e.name, e.department, COUNT(k) as commentCount
        FROM Kudos k
        JOIN k.receiver e
        WHERE k.createdAt >= :since
        AND k.type = 'comment'
        AND e.department = :department
        GROUP BY e.name, e.department
        ORDER BY COUNT(k) DESC
    """)
    List<Object[]> findTopEmployeesByDepartmentSince(@Param("department") String department, @Param("since") LocalDateTime since);

    // top 5 teams (still based on kudos is okay, or also comment if you want both consistent)
    @Query("""
        SELECT t.name, COUNT(k) as kudosCount
        FROM Kudos k
        JOIN k.targetTeam t
        WHERE k.createdAt >= :since
        AND k.type = 'kudos'
        GROUP BY t.name
        ORDER BY COUNT(k) DESC
    """)
    List<Object[]> findTopTeamsSince(@Param("since") LocalDateTime since);


}
