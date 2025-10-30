package com.kudossystem.kudossystem.controller;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.dto.EmployeeKudosDTO;
import com.kudossystem.kudossystem.repository.EmployeeRepository;
import com.kudossystem.kudossystem.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/search")
    public List<Employee> searchEmployees(@RequestParam(required = false) String keyword) {
        return employeeService.searchByNameOrDepartment(keyword);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // ✅ Upload employee CSV
    @PostMapping("/upload")
    public String uploadEmployeeCsv(@RequestParam("file") MultipartFile file) throws IOException {
        employeeService.uploadEmployeeCsv(file);
        return "Employees uploaded successfully!";
    }

    // ✅ Reset kudos counts
    @PostMapping("/reset-kudos")
    public String resetKudosCounts() {
        employeeService.resetKudosCounts();
        return "Kudos counts reset successfully!";
    }

    @GetMapping("/with-kudos")
    public List<EmployeeKudosDTO> getEmployeesWithKudos() {
        return employeeService.getEmployeesWithKudosOrComments();
    }


}

