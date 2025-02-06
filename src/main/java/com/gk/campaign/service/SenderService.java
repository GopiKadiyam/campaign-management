package com.gk.campaign.service;

import com.gk.campaign.models.Sender;

import java.util.List;

public interface SenderService {
    Sender createSender(Sender sender);

    Sender getSenderById(String senderId);

    List<Sender> getAllSenders();
}
