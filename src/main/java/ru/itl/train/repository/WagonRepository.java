package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itl.train.entity.WagonEntity;

import java.util.Optional;

/**
 * @author Kuznetsovka 23.07.2023
 */
public interface WagonRepository extends JpaRepository<WagonEntity, Long> {
    Optional<WagonEntity> findByWagonInfo_Number(Long number);
}
