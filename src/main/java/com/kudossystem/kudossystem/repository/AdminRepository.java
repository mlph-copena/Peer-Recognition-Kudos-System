package com.kudossystem.kudossystem.repository;

import com.kudossystem.kudossystem.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);

    // Case-insensitive email search
    @Query("SELECT a FROM Admin a WHERE LOWER(a.email) = LOWER(:email)")
    Optional<Admin> findByEmailIgnoreCase(@Param("email") String email);
}