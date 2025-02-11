package com.gk.campaign.mappers;

import com.gk.campaign.entities.postgres.TemplateEntity;
import com.gk.campaign.models.Template;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    @Mapping(target = "senderId",source = "entity.sender.senderId")
    Template templateEntityToTemplate(TemplateEntity entity);
    List<Template> templateEntitiesToTemplates(List<TemplateEntity> entities);
    TemplateEntity templateToTemplateEntity(Template template);
    List<TemplateEntity> templatesToTemplateEntities(List<Template> templates);
}
