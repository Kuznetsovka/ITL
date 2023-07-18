package ru.itl.train.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.itl.train.dto.User;
import ru.itl.train.entity.UserEntity;


/**
 * @author Kuznetsovka 18.07.2023
 */
public interface UserService extends UserDetailsService {


    void save(User userDto);

    void init();

    UserEntity findById(Long id);

    boolean isUserByLogin(String login);
}
