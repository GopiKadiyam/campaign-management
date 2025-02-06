package com.gk.campaign.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModelEntity implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on",nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on",nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
