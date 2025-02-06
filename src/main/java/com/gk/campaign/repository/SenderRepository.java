package com.gk.campaign.repository;

import com.gk.campaign.entities.SenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SenderRepository extends JpaRepository<SenderEntity,Long> {

    Optional<SenderEntity> findBySenderId(String senderId);
}
