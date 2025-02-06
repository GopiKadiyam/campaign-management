package com.gk.campaign.controllers;

import com.gk.campaign.entities.CampaignDataEntity;
import com.gk.campaign.exceptions.InvalidRequestException;
import com.gk.campaign.models.Campaign;
import com.gk.campaign.service.CampaignService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignServiceImpl;

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Map<String, Long> createCampaign(@RequestPart("campaign") @Valid Campaign campaign,
                                            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
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
    @PreAuthorize("hasRole('SUPER_ADMIN')")
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
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Campaign getCampaignById(@NotNull @PathVariable("id") Long campaignId) {
        return campaignServiceImpl.getCampaignById(campaignId);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Campaign> getAllCampaigns() {
        return campaignServiceImpl.getAllCampaigns();
    }
}
