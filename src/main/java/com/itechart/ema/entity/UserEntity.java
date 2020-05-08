package com.itechart.ema.entity;

import com.itechart.ema.entity.enums.UserRoleEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ema_user")
@EqualsAndHashCode(callSuper = false, of = "id")
public class UserEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "citext")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "title", nullable = false)
    private String title;

    @Type(type = "enumType")
    @Enumerated(STRING)
    @Column(name = "role", nullable = false, columnDefinition = "user_role")
    private UserRoleEntity role;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

}
