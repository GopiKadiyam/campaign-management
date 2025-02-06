package com.gk.campaign.repository;

import com.gk.campaign.entities.CampaignDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignDataRepository extends JpaRepository<CampaignDataEntity,Long> {
}
