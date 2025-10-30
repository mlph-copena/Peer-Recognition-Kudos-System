package com.kudossystem.kudossystem.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kudos_comments") // or "kudos" table
public class Kudos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Employee sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Employee receiver;

    @ManyToOne
    private Team targetTeam; // for team kudos

    private String message;
    private Boolean anonymous = false;

    @Enumerated(EnumType.STRING)
    private KudosType type; // KUDOS or COMMENT

    private LocalDateTime createdAt = LocalDateTime.now();
}
