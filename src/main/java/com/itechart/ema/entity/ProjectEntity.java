package com.itechart.ema.entity;

import com.itechart.ema.entity.enums.ProjectStatusEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
@EqualsAndHashCode(callSuper = false, of = "id")
public class ProjectEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Type(type = "enumType")
    @Enumerated(STRING)
    @Column(name = "status", nullable = false, columnDefinition = "project_status")
    private ProjectStatusEntity status;

    @Column(name = "estimation_in_weeks", nullable = false)
    private Integer estimationInWeeks;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private UserEntity manager;

    @Column(name = "manager_id", insertable = false, updatable = false, nullable = false)
    private UUID managerId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "scrum_master_id", nullable = false)
    private UserEntity scrumMaster;

    @Column(name = "scrum_master_id", insertable = false, updatable = false, nullable = false)
    private UUID scrumMasterId;

}
