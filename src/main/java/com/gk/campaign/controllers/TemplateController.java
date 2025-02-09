package com.gk.campaign.controllers;

import com.gk.campaign.service.TemplateService;
import com.gk.campaign.models.Template;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/template")
public class TemplateController {

   @Autowired
   private TemplateService templateServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createTemplate(@Valid @RequestBody Template template) {
        return ResponseEntity.ok(templateServiceImpl.createTemplate(template));
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<Template> getTemplateById(@PathVariable("templateId") String templateId) {
       return ResponseEntity.ok(templateServiceImpl.getTemplateById(templateId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Template>> getAllTemplates() {
        return ResponseEntity.ok(templateServiceImpl.getAllTemplates());
    }

    @GetMapping("/id/all")
    public ResponseEntity<List<String>> getALlSenderIds(){
        return ResponseEntity.ok(templateServiceImpl.getAllTemplateIds());
    }

    @GetMapping("/id/all/{senderId}")
    public ResponseEntity<List<String>> getALlSenderIds(@PathVariable("senderId")String senderId){
        return ResponseEntity.ok(templateServiceImpl.getAllTemplateIdsBySenderId(senderId));
    }
}
