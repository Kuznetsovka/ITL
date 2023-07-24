package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itl.train.entity.PartTrainEntity;

import java.util.Optional;

/**
 * @author Kuznetsovka 24.07.2023
 */
public interface PartTrainRepository extends JpaRepository<PartTrainEntity, Long> {
    boolean existsByOrderAndWagon_WagonInfo_Number(Long order, Long wagonNumber);

    Optional<PartTrainEntity> findByOrderAndWagon_WagonInfo_Number(Long order, Long wagonId);
}
