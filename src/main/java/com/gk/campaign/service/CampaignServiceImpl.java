package com.gk.campaign.service;

import com.gk.campaign.entities.CampaignDataEntity;
import com.gk.campaign.entities.CampaignEntity;
import com.gk.campaign.entities.SenderEntity;
import com.gk.campaign.entities.TemplateEntity;
import com.gk.campaign.exceptions.EntityNotFoundException;
import com.gk.campaign.mappers.CampaignMapper;
import com.gk.campaign.models.Campaign;
import com.gk.campaign.repository.CampaignDataRepository;
import com.gk.campaign.repository.CampaignRepository;
import com.gk.campaign.repository.SenderRepository;
import com.gk.campaign.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private CampaignMapper campaignMapper;
    @Autowired
    private CampaignDataRepository campaignDataRepository;

    @Override
    @Transactional
    public Map<String, Long> createCampaign(Campaign campaign, MultipartFile file, boolean isFileDataPresent) throws IOException {
        SenderEntity senderEntity = senderRepository.findBySenderId(campaign.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException(Map.of("Sender Id", "sender id :" + campaign.getSenderId() + " is nt found in db")));
        TemplateEntity templateEntity = templateRepository.findByTemplateId(campaign.getTemplateId())
                .orElseThrow(() -> new EntityNotFoundException(Map.of("Template Id", "template id :" + campaign.getTemplateId() + " is nt found in db")));
        CampaignEntity campaignEntity = campaignMapper.campaignToCampaignEntity(campaign);
        campaignEntity.setSender(senderEntity);
        campaignEntity.setTemplate(templateEntity);
        campaignEntity.setCampaignData(storeCampaignData(file, campaign.getCampaignData(), isFileDataPresent, campaignEntity));
        campaignEntity.setScheduleAt(LocalDateTime.now());

        campaignEntity = campaignRepository.save(campaignEntity);
        return Map.of("campaignID", campaignEntity.getId());
    }

    @Transactional
    public CampaignDataEntity storeCampaignData(MultipartFile file, String textCampaignData, boolean isFilePresent, CampaignEntity campaign) throws IOException {
        CampaignDataEntity campaignData = new CampaignDataEntity();
        campaignData.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        campaignData.setFileDataFlag(isFilePresent);
        campaignData.setType(file.getContentType());
        if (isFilePresent) {
            byte[] csvData = file.getBytes();
            campaignData.setFileData(csvData);
        } else {
            campaignData.setTextData(textCampaignData);
        }
        campaignData.setCampaign(campaign);
        //campaignData = campaignDataRepository.save(campaignData);
        return campaignData;
    }

    // Retrieve CSV File
    @Override
    @Transactional
    public CampaignDataEntity getCampaignFileData(Long campaignId) {
        CampaignEntity campaignEntity=getCampaignEntityById(campaignId);
        return campaignEntity.getCampaignData();
    }

    @Override
    @Transactional
    public Campaign getCampaignById(Long campaignId) {
        return campaignMapper.campaignEntityToCampaign(getCampaignEntityById(campaignId));

    }

    private CampaignEntity getCampaignEntityById(Long campaignId) {
        return campaignRepository.findById(campaignId.longValue())
                .orElseThrow(() -> new EntityNotFoundException(Map.of("Campaign Id", "campaign id :" + campaignId + " is nt found in db")));
    }

    @Override
    @Transactional
    public List<Campaign> getAllCampaigns() {
        return campaignMapper.campaignEntitiesToCampaigns(campaignRepository.findAll());
    }
}
