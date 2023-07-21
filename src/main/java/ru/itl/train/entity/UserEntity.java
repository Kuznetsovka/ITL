package ru.itl.train.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user",
        uniqueConstraints=@UniqueConstraint(
                name = "bk_tbl_user_login_password_idx", columnNames={"login", "password"}))
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    private String fio;

    @Column(name = "is_activate")
    @ColumnDefault("true")
    private boolean isActive;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}