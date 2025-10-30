package com.kudossystem.kudossystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeKudosDTO {
    private Long id;
    private String name;
    private String email;
    private String department;
    private Long kudosCount;   // changed from int → Long
    private Long commentCount; // changed from int → Long
}
