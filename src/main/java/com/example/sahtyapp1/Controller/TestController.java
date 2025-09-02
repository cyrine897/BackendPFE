package com.example.sahtyapp1.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/test")
public class TestController {



        @GetMapping("/all")
        public String allAccess() {
            return "Public Content.";
        }

        @GetMapping("/user")
            @PreAuthorize("hasRole('USER') or hasRole('Patient') or hasRole('ADMIN')  or hasRole('Medecin')  or hasRole('Pharmacien')")
        public String userAccess() {
            return "User Content.";
        }

        @GetMapping("/patient")
        @PreAuthorize("hasRole('Patient')")
        public String patientAccess() {
            return "Admin Board.";
        }

        @GetMapping("/admin")

        @PreAuthorize("hasRole('ADMIN')")
        public String adminAccess() {
            return "Admin Board.";
        }




    }

