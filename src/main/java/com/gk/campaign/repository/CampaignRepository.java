package com.gk.campaign.repository;

import com.gk.campaign.entities.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity,Long> {

    Optional<CampaignEntity> findById(Long campaignId);
}
