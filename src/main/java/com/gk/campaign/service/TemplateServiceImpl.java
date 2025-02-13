package com.gk.campaign.service;

import com.gk.campaign.entities.postgres.TemplateEntity;
import com.gk.campaign.exceptions.EntityNotFoundException;
import com.gk.campaign.mappers.TemplateMapper;
import com.gk.campaign.models.Template;
import com.gk.campaign.repository.postgres.SenderRepository;
import com.gk.campaign.repository.postgres.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private SenderRepository senderRepository;

    @Override
    @Transactional
    public Template createTemplate(Template template) {
        return senderRepository.findBySenderId(template.getSenderId()).map(senderEntity -> {
            TemplateEntity templateEntity = templateMapper.templateToTemplateEntity(template);
            templateEntity.setSender(senderEntity);
            return templateMapper.templateEntityToTemplate(templateRepository.save(templateEntity));
        }).orElseThrow(() -> new EntityNotFoundException(Map.of("senderId", "senderId: " + template.getSenderId() + " not found ")));
    }

    @Override
    @Transactional
    public Template getTemplateById(String templateId) {
        return templateRepository.findByTemplateId(templateId).map(templateEntity -> templateMapper.templateEntityToTemplate(templateEntity))
                .orElseThrow(() -> new EntityNotFoundException(Map.of("Template Id", "template id :" + templateId + " is nt found in db")));
    }

    @Override
    public Map<String, String> getTemplateMsgByTemplateById(String templateId) {
        return Map.of("templateBody", templateRepository.findTemplateBodyByTemplateId(templateId));
    }

    @Override
    @Transactional
    public List<Template> getAllTemplates() {
        return templateMapper.templateEntitiesToTemplates(templateRepository.findAll());
    }

    @Override
    public List<String> getAllTemplateIds() {
        return templateRepository.getAllTemplateIds();
    }

    @Override
    public List<String> getAllTemplateIdsBySenderId(String senderId) {
        return templateRepository.getAllTemplateIdsBySenderId(senderId);
    }
}
