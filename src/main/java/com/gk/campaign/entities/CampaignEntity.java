package com.gk.campaign.entities;

import com.gk.campaign.utils.enums.ServiceType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
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
@Entity
@Table(name = "campaign")
public class CampaignEntity extends AuditModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(max = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;

    @Column(name = "flash_flag")
    private boolean flashFlag;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id")
    private SenderEntity sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id")
    private TemplateEntity template;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "schedule_at")
    private LocalDateTime scheduleAt;

    @OneToOne(mappedBy = "campaign", fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
    private CampaignDataEntity campaignData;

    @Column(name = "total_no_of_msgs")
    private Long totalNoOfMsgs;

    @Column(name = "succeed_msgs")
    private Long succeedMsgs;

    @Column(name = "failed_msgs")
    private Long failedMsgs;

}
