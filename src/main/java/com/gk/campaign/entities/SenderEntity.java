package com.gk.campaign.entities;

import com.gk.campaign.utils.enums.CMRole;
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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sender")
public class SenderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String senderId;

    @NotBlank
    @Size(max = 20)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Country country;

    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    private ServiceType serviceType;

    @NotBlank
    @Size(max = 20)
    private String entityId;

}
