package ru.itl.train.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itl.train.entity.UserEntity;

/**
 * @author Kuznetsovka created 07.08.2022
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findFirstByLogin(String login);
}
