package com.itechart.ema.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team")
@EqualsAndHashCode(callSuper = false, of = "id")
public class TeamEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private UserEntity manager;

    @Column(name = "manager_id", insertable = false, updatable = false, nullable = false)
    private UUID managerId;

}
