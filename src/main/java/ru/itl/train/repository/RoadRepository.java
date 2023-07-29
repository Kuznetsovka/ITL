package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itl.train.entity.RoadEntity;

import java.util.Optional;

/**
 * @author Kuznetsovka 23.07.2023
 */
public interface RoadRepository extends JpaRepository<RoadEntity, Long> {

    Optional<RoadEntity> findByNumber(Long number);

    boolean existsByNumber(Long number);

    RoadEntity getByNumber(Long number);

    Optional<RoadEntity> findByNumberAndStation_Id(Long number, Long id);
}
