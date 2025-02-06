package com.gk.campaign.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
//@CrossOrigin(origins = "http://localhost:4200")
public class RoleController {

    @PostMapping("/add")
    public String adminAccess() {
        return "SUPER_ADMIN Board.";
    }
}
