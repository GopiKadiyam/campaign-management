package com.gk.campaign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DashboardAsyncTasks {

    @Autowired
    private MessageSenderServiceImpl messageSenderService;

    @Async("lightTaskExecutor")
    public void sendMessage(String phone, String message, Long campaignId) {
        messageSenderService.sendMessage(phone, message, campaignId);
    }
}
