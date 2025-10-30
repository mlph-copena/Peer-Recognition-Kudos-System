package com.kudossystem.kudossystem.controller;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.dto.ApiResponse; // make sure this is correct
import com.kudossystem.kudossystem.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final EmployeeService employeeService;

    // ---------- Admin Dashboard ----------
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        model.addAttribute("allEmployees", allEmployees);
        return "admin-dashboard"; // admin-dashboard.html
    }

    // ---------- Upload Employee CSV ----------
    @PostMapping("/upload-employees")
    public ResponseEntity<ApiResponse> uploadEmployees(@RequestParam("file") MultipartFile file) {
        try {
            // Optionally, return a summary: employees added / skipped
            employeeService.uploadEmployeeCsv(file);

            return ResponseEntity.ok(new ApiResponse(
                    HttpStatus.OK.value(),
                    "Employee CSV uploaded successfully!",
                    System.currentTimeMillis()
            ));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error reading CSV file: %s".formatted(e.getMessage()),
                            System.currentTimeMillis()
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error uploading employees: %s".formatted(e.getMessage()),
                            System.currentTimeMillis()
                    ));
        }
    }

    // ---------- List All Employees ----------
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        return ResponseEntity.ok(allEmployees);
    }


    // ---------- Reset Kudos Counts ----------
    @PostMapping("/reset-kudos")
    public ResponseEntity<ApiResponse> resetKudos() {
        try {
            employeeService.resetKudosCounts();
            return ResponseEntity.ok(new ApiResponse(
                    HttpStatus.OK.value(),
                    "Kudos counts reset successfully!",
                    System.currentTimeMillis()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error resetting kudos counts: %s".formatted(e.getMessage()),
                            System.currentTimeMillis()
                    ));
        }
    }

    @GetMapping("/search-employees")
    @ResponseBody
    public List<Employee> searchEmployees(@RequestParam String keyword) {
        return employeeService.searchByNameOrDepartment(keyword);
    }

}
