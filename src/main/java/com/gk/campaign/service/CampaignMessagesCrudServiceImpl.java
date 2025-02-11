package com.gk.campaign.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.campaign.entities.redis.CampaignMessage;
import com.gk.campaign.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CampaignMessagesCrudServiceImpl {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private static  final String CAMPAIGN_KEY="campaign:%d:phone:%s";

    public void saveIndividualCampaign(CampaignMessage campaignMessage){
        try {
            //String redisKey = "campaign:" + individualCampaign.getCampaignId() + ":phone:" + individualCampaign.getPhoneNumber();
            String redisKey=String.format(CAMPAIGN_KEY, campaignMessage.getCampaignId(), campaignMessage.getPhoneNumber());
            Map ruleHash = new ObjectMapper().convertValue(campaignMessage, Map.class);
            redisTemplate.opsForHash().putAll(redisKey,ruleHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CampaignMessage getIndividualCampaign(String campaignId, String phone){
        //String redisKey = "campaign:" + campaignId + ":phone:" + phone;
        String redisKey=String.format(CAMPAIGN_KEY,campaignId,phone);
        Map<Object,Object> resultMap= redisTemplate.opsForHash().entries(redisKey);
        CampaignMessage result = new ObjectMapper().convertValue(resultMap, CampaignMessage.class);
        return result;
    }

    public List<Map<Object,Object>> getAllIndividualCampaignForCampaignId(String campaignId){
        String redisKey = "campaign:" + campaignId + ":phone:";
        Set<String> keys=redisTemplate.keys(redisKey+"*");
        if(keys == null || keys.isEmpty())
            return Collections.emptyList();
        return keys.stream()
                .map(key->redisTemplate.opsForHash().entries(key))
                .collect(Collectors.toList());
    }

    public void updateIndividualCampaign(String campaignId,String phone,String field,Object newValue){
        String redisKey=String.format(CAMPAIGN_KEY,campaignId,phone);
        if(Boolean.FALSE.equals(redisTemplate.hasKey(redisKey)))
            throw new EntityNotFoundException(Map.of(redisKey,redisKey+" not present in the db"));
        redisTemplate.opsForHash().put(redisKey,field,newValue);
    }
}
