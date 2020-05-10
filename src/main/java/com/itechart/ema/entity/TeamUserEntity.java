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
@Table(name = "team_user")
@EqualsAndHashCode(callSuper = false, of = "id")
public class TeamUserEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    @Column(name = "team_id", insertable = false, updatable = false, nullable = false)
    private UUID teamId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "added_at", insertable = false, updatable = false, nullable = false)
    private Instant addedAt;

}
