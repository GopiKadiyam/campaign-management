package com.gk.campaign.entities.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign_file")
public class CampaignDataEntity extends AuditModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    private String type;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "campaign_id")
    private CampaignEntity campaign;

    @Lob // Large Object (Used for BYTEA)
    @Column(name = "file_data")
    private byte[] fileData;

    @Lob // Large Object (Used for BYTEA)
    @Column(name = "text_data")
    private String textData;

    @Column(name = "is_file_data")
    private boolean fileDataFlag;
}
