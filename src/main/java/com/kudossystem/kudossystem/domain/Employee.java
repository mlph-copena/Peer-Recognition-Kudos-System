package com.kudossystem.kudossystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "email")
    private String email;
    private String password; // needed
    private String department;
    private int kudosCount = 0;
    private int commentCount = 0; // <-- add this

    @ManyToMany(mappedBy = "members")
    private Set<Team> teams = new HashSet<>();

    @Column(length = 500)
    private String comments; // public kudos or remarks
}