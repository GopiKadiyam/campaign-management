package com.gk.campaign.mappers;

import com.gk.campaign.entities.CampaignEntity;
import com.gk.campaign.models.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CampaignMapper {

    @Mapping(target = "senderId",source = "sender.senderId")
    @Mapping(target = "templateId",source = "template.templateId")
    @Mapping(target = "campaignData",ignore = true)
    @Mapping(target = "campaignDataId",source = "campaignData.id")
    Campaign campaignEntityToCampaign(CampaignEntity campaignEntity);

    List<Campaign> campaignEntitiesToCampaigns(List<CampaignEntity> campaignEntities);

    @Mapping(target = "campaignData",ignore = true)
    CampaignEntity campaignToCampaignEntity(Campaign campaign);

    List<CampaignEntity> campaignsToCampaignEntities(List<Campaign> campaigns);

}
