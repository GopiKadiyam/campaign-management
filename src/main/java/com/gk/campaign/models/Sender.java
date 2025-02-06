package com.gk.campaign.models;

import com.gk.campaign.utils.enums.Country;
import com.gk.campaign.utils.enums.ServiceType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sender extends AuditModel{

    private Long id;
    @NotBlank
    private String senderId;
    @NotBlank
    private String description;
    private Country country;
    private ServiceType serviceType;
    private String entityId;
    private String isOpen;
    @NotNull
    private boolean statusFlag;

}
