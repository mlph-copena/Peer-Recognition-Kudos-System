package com.kudossystem.kudossystem.dto;

public class DTO {

    public record EmployeeLeaderboardDto(Long id, String name, int kudosCount) {}
    public record TeamLeaderboardDto(Long id, String name, int kudosCount) {}

}
