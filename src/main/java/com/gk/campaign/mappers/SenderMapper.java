package com.gk.campaign.mappers;

import com.gk.campaign.entities.SenderEntity;
import com.gk.campaign.models.Sender;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SenderMapper {

    Sender senderEntityToSenderResponse(SenderEntity senderEntity);
    List<Sender> senderEntitiesToSenderResponses(List<SenderEntity> senderEntity);
    SenderEntity senderToSenderEntity(Sender sender);
}
