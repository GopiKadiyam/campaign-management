package com.gk.campaign.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @PostMapping("/add")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String adminAccess() {
        return "SUPER_ADMIN Board.";
    }
}
