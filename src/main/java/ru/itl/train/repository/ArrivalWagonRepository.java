package ru.itl.train.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itl.train.entity.ArrivalWagonEntity;

/**
 * @author Kuznetsovka 23.07.2023
 */
public interface ArrivalWagonRepository extends JpaRepository<ArrivalWagonEntity, Long> {

}
