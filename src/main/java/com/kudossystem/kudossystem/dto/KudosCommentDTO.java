package com.kudossystem.kudossystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class KudosCommentDTO {
    private Long id;
    private String senderName;
    private String receiverName;
    private String teamName;
    private String message;
    private String type;
    private Boolean anonymous;
    private LocalDateTime createdAt;
}