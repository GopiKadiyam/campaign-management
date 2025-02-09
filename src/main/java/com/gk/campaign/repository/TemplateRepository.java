package com.gk.campaign.repository;

import com.gk.campaign.entities.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity,Long> {

    Optional<TemplateEntity> findByTemplateId(String templateId);

    @Query("SELECT te.templateId FROM TemplateEntity te")
    List<String> getAllTemplateIds();

    @Query("SELECT te.templateId FROM TemplateEntity te WHERE  te.sender.senderId = ?1")
    List<String> getAllTemplateIdsBySenderId(String senderId);

}
