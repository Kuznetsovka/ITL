package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.entity.StationEntity;

import java.util.Optional;

/**
 * @author Kuznetsovka 23.07.2023
 */
public interface StationRepository extends JpaRepository<StationEntity, Long> {

    Optional<StationEntity> findById(Long id);

    boolean existsById(Long id);

    StationEntity getById(Long id);

    @Query("select s.road from StationEntity s inner join s.road r where r.number =:number and s.id = :id")
    Optional<RoadEntity> getRoadByNumberAndStationId(Long number, Long id);
}
