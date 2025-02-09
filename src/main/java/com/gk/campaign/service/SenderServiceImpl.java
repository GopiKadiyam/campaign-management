package com.gk.campaign.service;

import com.gk.campaign.entities.SenderEntity;
import com.gk.campaign.exceptions.EntityNotFoundException;
import com.gk.campaign.mappers.SenderMapper;
import com.gk.campaign.models.Sender;
import com.gk.campaign.repository.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class SenderServiceImpl implements SenderService {

    @Autowired
    private SenderMapper senderMapper;
    @Autowired
    private SenderRepository senderRepository;

    @Override
    @Transactional
    public Sender createSender(Sender sender) {
        SenderEntity senderEntity=senderMapper.senderToSenderEntity(sender);
        return senderMapper.senderEntityToSenderResponse(senderRepository.save(senderEntity));
    }

    @Override
    @Transactional
    public Sender getSenderById(String senderId) {
        SenderEntity senderEntity=senderRepository.findBySenderId(senderId)
                .orElseThrow(()-> new EntityNotFoundException(Map.of("Sender Id","sender id :"+senderId+" is nt found in db")));
        return senderMapper.senderEntityToSenderResponse(senderEntity);
    }

    @Override
    @Transactional
    public List<Sender> getAllSenders() {
        return senderMapper.senderEntitiesToSenderResponses(senderRepository.findAll());
    }

    @Override
    public List<String> getAllSenderIds() {
        return senderRepository.getAllSenderIds();
    }
}
