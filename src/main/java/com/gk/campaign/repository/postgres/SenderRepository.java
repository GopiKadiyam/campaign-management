package com.gk.campaign.repository.postgres;

import com.gk.campaign.entities.postgres.SenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SenderRepository extends JpaRepository<SenderEntity,Long> {

    Optional<SenderEntity> findBySenderId(String senderId);

    @Query("SELECT se.senderId FROM SenderEntity se")
    List<String> getAllSenderIds();
}
