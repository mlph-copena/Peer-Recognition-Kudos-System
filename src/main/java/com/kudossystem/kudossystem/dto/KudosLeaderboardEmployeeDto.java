package com.kudossystem.kudossystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KudosLeaderboardEmployeeDto {
    private String name;
    private String department;
    private long kudosCount;
}
