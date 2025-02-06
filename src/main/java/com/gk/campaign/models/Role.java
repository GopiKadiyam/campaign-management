package com.gk.campaign.models;

import com.gk.campaign.utils.enums.CMRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AuditModel{

  private Integer id;

  @Column(length = 20)
  private CMRole name;

}