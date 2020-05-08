package com.itechart.ema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

import static java.time.Instant.now;

@Data
@MappedSuperclass
public abstract class AbstractBaseEntity {

    @JsonIgnore
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    protected Instant updatedAt;

    @JsonIgnore
    @Column(name = "deleted_at", insertable = false)
    protected Instant deletedAt;

    @PrePersist
    public void prePersist() {
        setCreatedAt(now());
    }

    @PreUpdate
    public void preUpdate() {
        setUpdatedAt(now());
    }

}
