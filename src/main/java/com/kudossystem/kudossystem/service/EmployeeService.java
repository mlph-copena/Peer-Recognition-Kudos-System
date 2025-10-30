package com.kudossystem.kudossystem.service;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.dto.EmployeeKudosDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee saveEmployee(Employee employee);

    void uploadEmployeeCsv(MultipartFile file) throws IOException;

    void resetKudosCounts();

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByName(String name);

    List<Employee> searchByNameOrDepartment(String keyword);

    List<EmployeeKudosDTO> getEmployeesWithKudosOrComments();
}