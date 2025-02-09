package com.gk.campaign.service;

import com.gk.campaign.models.Template;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TemplateService {

    Template createTemplate(Template template);

    Template getTemplateById(String templateId);

    List<Template> getAllTemplates();

    List<String> getAllTemplateIds();

    List<String> getAllTemplateIdsBySenderId(String senderId);
}
