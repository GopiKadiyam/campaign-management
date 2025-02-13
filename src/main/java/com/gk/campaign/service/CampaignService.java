package com.gk.campaign.service;

import com.gk.campaign.entities.postgres.CampaignDataEntity;
import com.gk.campaign.models.Campaign;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CampaignService {

    Map<String,String> createCampaign(Campaign campaign, MultipartFile file,boolean isFileDataPresent) throws Exception;
    Campaign getCampaignById(Long campaignId);
    List<Campaign> getAllCampaigns();
    CampaignDataEntity getCampaignFileData(Long fileId);
}
