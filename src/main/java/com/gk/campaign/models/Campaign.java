package com.gk.campaign.models;

import com.gk.campaign.utils.enums.ServiceType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Campaign extends AuditModel {

    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 500)
    private String description;
    private ServiceType serviceType;
    private boolean flashFlag;
    @NotBlank
    private String senderId;
    @NotBlank
    private String templateId;
    private String campaignData;
    private LocalDateTime scheduleAt;

    private Long campaignDataId;
}
