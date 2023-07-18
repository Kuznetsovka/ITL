package ru.itl.train.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itl.train.entity.Role;

/**
 * @author Kuznetsovka 18.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String login;
    private String password;
    private String fio;
    private boolean isActive;
    private Role role = Role.USER;
}
