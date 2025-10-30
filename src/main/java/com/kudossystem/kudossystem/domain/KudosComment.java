package com.kudossystem.kudossystem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KudosComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ Hibernate needs this!

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Employee sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = true)
    private Employee receiver;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = true)
    private Team targetTeam;

    private String message;

    private boolean anonymous;

    private String type; // "kudos" or "comment"

    private LocalDateTime createdAt = LocalDateTime.now();
}
