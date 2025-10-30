package com.kudossystem.kudossystem.repository;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.dto.EmployeeKudosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByName(String name);
    Optional<Employee> findByEmail(String email);

    List<Employee> findByNameContainingIgnoreCase(String name);

    // search by name or department (case-insensitive)
    List<Employee> findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String name, String department);

    // 🔍 Search by employee name, department, OR team name
    @Query("""
        SELECT DISTINCT e FROM Employee e
        LEFT JOIN e.teams t
        WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Employee> searchByKeyword(@Param("keyword") String keyword);

    @Query("""
        SELECT new com.kudossystem.kudossystem.dto.EmployeeKudosDTO(
            e.id, e.name, e.email, e.department,
            COALESCE(SUM(CASE WHEN k.type = 'kudos' THEN 1 ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN k.type = 'comment' THEN 1 ELSE 0 END), 0)
        )
        FROM Employee e
        LEFT JOIN Kudos k ON k.receiver = e
        GROUP BY e.id, e.name, e.email, e.department
        HAVING COUNT(k) > 0
    """)
    List<EmployeeKudosDTO> findEmployeesWithKudosOrComments();


    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET e.kudosCount = 0")
    void resetAllKudosCounts();

}