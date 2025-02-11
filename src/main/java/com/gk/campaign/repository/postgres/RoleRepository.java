package com.gk.campaign.repository.postgres;

import com.gk.campaign.entities.postgres.RoleEntity;
import com.gk.campaign.utils.enums.CMRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  Optional<RoleEntity> findByName(CMRole name);
}
