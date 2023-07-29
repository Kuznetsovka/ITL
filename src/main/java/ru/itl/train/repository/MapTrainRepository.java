package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itl.train.entity.MapTrainEntity;

import java.util.Optional;

/**
 * @author Kuznetsovka 24.07.2023
 */
public interface MapTrainRepository extends JpaRepository<MapTrainEntity, Long> {
    Optional<MapTrainEntity> findByRoad_Number(Long number);
}
