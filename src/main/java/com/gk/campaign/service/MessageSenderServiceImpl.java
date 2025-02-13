package com.gk.campaign.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.campaign.entities.redis.CampaignMessage;
import com.gk.campaign.utils.enums.CampaignMessageStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MessageSenderServiceImpl {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Async("lightTaskExecutor")
    public void sendMessage(String phoneNumber, String message, Long campaignId) {
        String apiUrl = "https://thirdparty.com/sendMessage";

        Map<String, String> payload = new HashMap<>();
        payload.put("phone", phoneNumber);
        payload.put("message", message);
        try {
            CampaignMessage campaignMessage = new CampaignMessage();
            campaignMessage.setCampaignId(campaignId);
            campaignMessage.setCampaignMessageStatus(CampaignMessageStatus.REQUESTED);
            campaignMessage.setMessage(message);
            campaignMessage.setCampaignRequest(null);
            campaignMessage.setPhoneNumber(phoneNumber);
            String redisKey = "campaign:" + campaignMessage.getCampaignId() + ":phone:" + campaignMessage.getPhoneNumber();
            Map ruleHash = new ObjectMapper().convertValue(campaignMessage, Map.class);
            redisTemplate.opsForHash().putAll(redisKey, ruleHash);
            log.info("message sent for phone {} and campaignId {}", phoneNumber, campaignId);
        } catch (Exception e) {
            // Store failure status in Redis
            System.out.println(e);
            log.info("message failed for phone {} and campaignId {} and message {}", phoneNumber, campaignId, message);
        }
    }
}
