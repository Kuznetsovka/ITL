package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itl.train.entity.MapTrainEntity;

import java.util.Optional;

/**
 * @author Kuznetsovka 24.07.2023
 */
public interface MapTrainRepository extends JpaRepository<MapTrainEntity, Long> {
    @Query("select map from MapTrainEntity map inner join map.road r where r.number = :number")
    Optional<MapTrainEntity> findByRoad_Number(Long number);
}
