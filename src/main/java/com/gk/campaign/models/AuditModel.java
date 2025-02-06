package com.gk.campaign.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditModel implements Serializable {

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
}
