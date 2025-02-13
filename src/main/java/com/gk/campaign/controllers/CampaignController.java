package com.gk.campaign.controllers;

import com.gk.campaign.entities.postgres.CampaignDataEntity;
import com.gk.campaign.entities.redis.CampaignMessage;
import com.gk.campaign.exceptions.InvalidRequestException;
import com.gk.campaign.models.Campaign;
import com.gk.campaign.service.CampaignService;
import com.gk.campaign.service.CampaignMessagesCrudServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignServiceImpl;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public Map<String, String> createCampaign(@RequestPart("campaign") @Valid Campaign campaign,
                                            @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
        boolean isFileDataPresent;
        if (file != null) {
            isFileDataPresent = true;
        } else if (campaign.getCampaignData() != null && !campaign.getCampaignData().isBlank()) {
            isFileDataPresent = false;
        } else {
            throw new InvalidRequestException(Map.of("campaignData", "campaignData must be upload either via file or as text"));
        }
        return campaignServiceImpl.createCampaign(campaign, file, isFileDataPresent);
    }

    @GetMapping("/{campaignId}/file")
    public ResponseEntity<byte[]> downloadCsv(@PathVariable Long campaignId) {
        CampaignDataEntity fileEntity = campaignServiceImpl.getCampaignFileData(campaignId);

        if (fileEntity == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileEntity.getFileName())
                .contentType(MediaType.parseMediaType(fileEntity.getType()))
                .body(fileEntity.getFileData());
    }

    @GetMapping("/{id}")
    public Campaign getCampaignById(@NotNull @PathVariable("id") Long campaignId) {
        return campaignServiceImpl.getCampaignById(campaignId);
    }

    @GetMapping("/all")
    public List<Campaign> getAllCampaigns() {
        return campaignServiceImpl.getAllCampaigns();
    }

    @Autowired
    private CampaignMessagesCrudServiceImpl campaignMessagesCrudService;
    @GetMapping("/{cId}/message/{phone}")
    public CampaignMessage getIndividualMessageOfIndividualCampaign(@PathVariable("cId")String cId, @PathVariable("phone")String phone) {
        return campaignMessagesCrudService.getIndividualMessageOfIndividualCampaign(cId,phone);
    }
    @GetMapping("/{cId}/message/all")
    public List<Map<Object,Object>> getAllMessagesForCampaignId(@PathVariable("cId")String cId) {
        return campaignMessagesCrudService.getAllMessagesForCampaignId(cId);
    }

    @GetMapping("/{cId}/message/stats")
    public Map<Object,Object> getMessagesStatsForCampaign(@PathVariable("cId")String cId) {
        return campaignMessagesCrudService.getMessagesStatsForCampaign(cId);
    }
}
