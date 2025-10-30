package com.kudossystem.kudossystem.service.impl;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.dto.EmployeeKudosDTO;
import com.kudossystem.kudossystem.repository.EmployeeRepository;
import com.kudossystem.kudossystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void uploadEmployeeCsv(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Uploaded file is empty.");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;
            int added = 0;
            int skipped = 0;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header
                }

                String[] data = line.split(",");

                // Validate number of columns
                if (data.length < 3) {
                    skipped++;
                    continue;
                }

                String name = data[0].trim();
                String email = data[1].trim();
                String department = data[2].trim();
                String password = data.length > 3 ? data[3].trim() : "password"; // default password

                if (name.isEmpty() || email.isEmpty() || department.isEmpty()) {
                    skipped++;
                    continue;
                }

                // prevent duplicate email
                Optional<Employee> existing = employeeRepository.findByEmail(email);
                if (existing.isPresent()) {
                    skipped++;
                    continue;
                }

                Employee employee = new Employee();
                employee.setName(name);
                employee.setEmail(email);
                employee.setDepartment(department);
                employee.setPassword(password);
                employee.setKudosCount(0);

                employeeRepository.save(employee);
                added++;
            }

            System.out.printf("CSV Upload Summary: Added %d employees, Skipped %d duplicates or invalid rows%n",
                    added, skipped);
        }
    }

    @Override
    public void resetKudosCounts() {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            employee.setKudosCount(0);
        }
        employeeRepository.saveAll(employees);
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public Optional<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public List<Employee> searchByNameOrDepartment(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll();
        }
        return employeeRepository.searchByKeyword(keyword);
    }

    @Override
    public List<EmployeeKudosDTO> getEmployeesWithKudosOrComments() {
        return employeeRepository.findEmployeesWithKudosOrComments();
    }
}