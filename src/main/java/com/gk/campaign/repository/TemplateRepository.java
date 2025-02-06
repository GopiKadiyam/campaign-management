package com.gk.campaign.repository;

import com.gk.campaign.entities.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity,Long> {

    Optional<TemplateEntity> findByTemplateId(String templateId);
}
