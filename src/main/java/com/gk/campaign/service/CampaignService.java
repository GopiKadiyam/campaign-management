package com.gk.campaign.service;

import com.gk.campaign.entities.CampaignDataEntity;
import com.gk.campaign.models.Campaign;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CampaignService {

    Map<String,Long> createCampaign(Campaign campaign, MultipartFile file,boolean isFileDataPresent) throws IOException;
    Campaign getCampaignById(Long campaignId);
    List<Campaign> getAllCampaigns();
    CampaignDataEntity getCampaignFileData(Long fileId);
}
