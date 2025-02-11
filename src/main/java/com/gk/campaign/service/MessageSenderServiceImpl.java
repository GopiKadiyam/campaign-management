package com.gk.campaign.service;

import com.gk.campaign.entities.redis.CampaignMessage;
import com.gk.campaign.utils.enums.CampaignMessageStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageSenderServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CampaignMessagesCrudServiceImpl campaignMessagesCrudService;

    public void sendMessage(String phoneNumber,String message,Long campaignId){
        String apiUrl = "https://thirdparty.com/sendMessage";

        Map<String, String> payload = new HashMap<>();
        payload.put("phone", phoneNumber);
        payload.put("message", message);
        try {
            String response = restTemplate.postForObject(apiUrl, payload, String.class);
            // Store success status in Redis
            CampaignMessage campaignMessage =new CampaignMessage();
            campaignMessage.setCampaignId(campaignId);
            campaignMessage.setCampaignMessageStatus(CampaignMessageStatus.SUCCESS);
            campaignMessage.setMessage(message);
            campaignMessage.setCampaignRequest(null);
            campaignMessage.setPhoneNumber(phoneNumber);

            campaignMessagesCrudService.saveIndividualCampaign(campaignMessage);
        } catch (Exception e) {
            // Store failure status in Redis
            CampaignMessage campaignMessage =new CampaignMessage();
            campaignMessage.setCampaignId(campaignId);
            campaignMessage.setCampaignMessageStatus(CampaignMessageStatus.FAILURE);
            campaignMessage.setMessage(message);
            campaignMessage.setCampaignRequest(null);
            campaignMessage.setPhoneNumber(phoneNumber);

            campaignMessagesCrudService.saveIndividualCampaign(campaignMessage);
        }

    }
}
