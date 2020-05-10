package com.itechart.ema.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_user")
@EqualsAndHashCode(callSuper = false, of = "id")
public class ProjectUserEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "project_id", insertable = false, updatable = false, nullable = false)
    private UUID projectId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "added_at", insertable = false, updatable = false, nullable = false)
    private Instant addedAt;

}
