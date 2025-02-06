package com.gk.campaign.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String test() {
        return "Hello World !";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/super-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String adminAccess() {
        return "SUPER_ADMIN Board.";
    }

}
