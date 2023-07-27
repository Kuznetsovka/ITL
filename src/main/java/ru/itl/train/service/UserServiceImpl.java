package ru.itl.train.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.User;
import ru.itl.train.entity.Role;
import ru.itl.train.entity.UserEntity;
import ru.itl.train.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuznetsovka 18.07.2023
 */

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${spring.security.user.name}")
    private String login;

    @Value("${spring.security.user.password}")
    private String password;

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc }
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(User userDto) {
        UserEntity user = userDto.getId() != null ? findById(userDto.getId()) : null;
        UserEntity userNew = UserEntity.builder()
                .login(userDto.getLogin())
                .isActive(userDto.isActive())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .fio(userDto.getFio())
                .role(userDto.getRole())
                .build();
        if (user != null) {
            if (userDto.getLogin() == null || userDto.getLogin().isEmpty())
                userNew.setLogin(user.getLogin());
            else
                throw new IllegalArgumentException("Сохранение пользователя с пустым логин не возможно.");
            if (userDto.getPassword() == null || userDto.getPassword().isEmpty())
                userNew.setPassword(user.getPassword());
            else
                throw new IllegalArgumentException("Сохранение пользователя с пустым паролем не возможно.");
            userNew.setId(user.getId());
        }
        userRepository.save(userNew);
        log.info("Пользователь {} сохранен", userNew.getLogin());
    }

    @PostConstruct
    @Transactional
    @Override
    public void init() {
        // Создание первого пользователя с ролью admin если не найден.
        User user = User.builder()
                .login(login)
                .isActive(true)
                .password(password)
                .fio("Kuznetsov Kirill Alekseevich")
                .role(Role.ADMIN).build();
        if (!isUserByLogin(user.getLogin())) {
            save(user);
            log.info("Создание первого пользователя с ролью Admin");
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isUserByLogin(String login) {
        return userRepository.findFirstByLogin(login) != null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity user = userRepository.findFirstByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }
        if (!user.isActive()) {
            throw new UsernameNotFoundException("User not active with login: " + login);
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                roles);
    }
}
