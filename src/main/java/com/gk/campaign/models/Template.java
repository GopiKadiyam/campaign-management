package com.gk.campaign.models;

import com.gk.campaign.utils.enums.ServiceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template extends AuditModel{

    private Long id;
    @Size(max = 50)
    private String templateId;
    @Size(max = 500)
    private String templateBody;
    @Size(max = 100)
    private String name;
    @Size(max = 500)
    private String description;
    private String senderId;
    private ServiceType serviceType;
    private boolean activeStatusFlag;

}
