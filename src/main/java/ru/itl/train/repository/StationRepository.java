package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.entity.StationEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 23.07.2023
 */
public interface StationRepository extends JpaRepository<StationEntity, Long> {

    Optional<StationEntity> findById(Long id);

    boolean existsById(Long id);

    StationEntity getById(Long id);

    @Query("select r from StationEntity s inner join s.roads r where r.number =:number and s.id = :id")
    Optional<RoadEntity> getRoadByNumberAndStationId(Long number, Long id);

    @Query("select (case when count(distinct s.id) = 1 then true else false end) from StationEntity s inner join s.roads r where r.number in (:roads)")
    boolean existStationRoads(List<Long> roads);
}
