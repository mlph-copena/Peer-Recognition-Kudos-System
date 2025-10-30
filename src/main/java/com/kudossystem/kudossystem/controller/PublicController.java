package com.kudossystem.kudossystem.controller;

import com.kudossystem.kudossystem.domain.Employee;
import com.kudossystem.kudossystem.domain.Team;
import com.kudossystem.kudossystem.domain.Kudos;
import com.kudossystem.kudossystem.service.KudosService;
import com.kudossystem.kudossystem.repository.EmployeeRepository;
import com.kudossystem.kudossystem.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PublicController {

//    private final KudosService kudosService;
//    private final EmployeeRepository employeeRepository;
//
//    // Public endpoint to send kudos by employee names
//    @PostMapping("/send")
//    public Kudos sendKudos(@RequestParam String senderName,
//                           @RequestParam String receiverName,
//                           @RequestParam String message,
//                           @RequestParam(defaultValue = "false") boolean anonymous) {
//
//        // Convert sender name to Employee entity
//        Employee sender = employeeRepository.findByName(senderName)
//                .orElseThrow(() -> new RuntimeException("Sender not found"));
//
//        // Convert receiver name to Employee entity
//        Employee receiver = employeeRepository.findByName(receiverName)
//                .orElseThrow(() -> new RuntimeException("Receiver not found"));
//
//        // Call the service with Employee entities
//        return kudosService.sendKudos(sender, receiver, message, anonymous);
//    }
}
