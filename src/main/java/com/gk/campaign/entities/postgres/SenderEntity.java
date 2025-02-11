package com.gk.campaign.entities.postgres;

import com.gk.campaign.utils.enums.Country;
import com.gk.campaign.utils.enums.ServiceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sender")
public class SenderEntity extends AuditModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "sender_id",unique = true)
    private String senderId;

    @NotBlank
    @Size(max = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;

    @NotBlank
    @Size(max = 50)
    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "is_open")
    private String isOpen;

    @NotNull
    @Column(name = "status_flag")
    private boolean statusFlag;

}
