package com.gk.campaign.controllers;

import com.gk.campaign.service.SenderService;
import com.gk.campaign.models.Sender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sender")
public class SenderController {
    @Autowired
    private SenderService senderServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createSender(@Valid @RequestBody Sender sender) {
        return ResponseEntity.ok(senderServiceImpl.createSender(sender));
    }

    @GetMapping("/{senderId}")
    public ResponseEntity<Sender> getSenderById(@NotNull @NotBlank @PathVariable("senderId") String senderId) {
        return ResponseEntity.ok(senderServiceImpl.getSenderById(senderId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sender>> getAllSenders() {
        return ResponseEntity.ok(senderServiceImpl.getAllSenders());
    }

    @GetMapping("/id/all")
    public ResponseEntity<List<String>> getALlSenderIds(){
        return ResponseEntity.ok(senderServiceImpl.getAllSenderIds());
    }
}
