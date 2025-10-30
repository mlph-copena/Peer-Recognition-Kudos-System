package com.kudossystem.kudossystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KudosLeaderboardTeamDto {
    private String name;
    private long kudosCount;
}