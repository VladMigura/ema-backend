package com.itechart.ema.entity;

import com.itechart.ema.entity.enums.TaskPriorityEntity;
import com.itechart.ema.entity.enums.TaskStatusEntity;
import com.itechart.ema.entity.enums.TaskTypeEntity;
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
@Table(name = "task")
@EqualsAndHashCode(callSuper = false, of = "id")
public class TaskEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Type(type = "enumType")
    @Enumerated(STRING)
    @Column(name = "type", nullable = false, columnDefinition = "task_type")
    private TaskTypeEntity type;

    @Type(type = "enumType")
    @Enumerated(STRING)
    @Column(name = "priority", nullable = false, columnDefinition = "task_priority")
    private TaskPriorityEntity priority;

    @Type(type = "enumType")
    @Enumerated(STRING)
    @Column(name = "status", nullable = false, columnDefinition = "task_status")
    private TaskStatusEntity status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "project_id", insertable = false, updatable = false, nullable = false)
    private UUID projectId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dev_owner_id", nullable = false)
    private UserEntity devOwner;

    @Column(name = "dev_owner_id", insertable = false, updatable = false, nullable = false)
    private UUID devOwnerId;

    @Column(name = "estimation_in_hours", nullable = false)
    private Integer estimationInHours;

    @Column(name = "estimation_in_points", nullable = false)
    private Integer estimationInPoints;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @Column(name = "created_by", insertable = false, updatable = false, nullable = false)
    private UUID createdById;

}
