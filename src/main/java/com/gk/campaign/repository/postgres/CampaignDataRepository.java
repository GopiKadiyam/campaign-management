package com.gk.campaign.repository.postgres;

import com.gk.campaign.entities.postgres.CampaignDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignDataRepository extends JpaRepository<CampaignDataEntity,Long> {
}
