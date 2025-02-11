package com.gk.campaign.entities.redis;

import com.gk.campaign.utils.enums.CampaignMessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignMessage implements Serializable {
    private Long campaignId;
    private String phoneNumber;
    private String message;
    private String campaignRequest;
    private CampaignMessageStatus campaignMessageStatus;
}
