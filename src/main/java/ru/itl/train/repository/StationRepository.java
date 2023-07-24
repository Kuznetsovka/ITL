package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itl.train.entity.StationEntity;

import java.util.Optional;

/**
 * @author Kuznetsovka 23.07.2023
 */
public interface StationRepository extends JpaRepository<StationEntity, Long> {

    Optional<StationEntity> findById(Long id);

    boolean existsById(Long id);

    StationEntity getById(Long id);
}
