package com.gk.campaign.repository.redis;

import com.gk.campaign.entities.redis.CampaignMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignResultRepository extends CrudRepository<CampaignMessage, Long> {
}
