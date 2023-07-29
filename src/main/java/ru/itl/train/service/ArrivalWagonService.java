package ru.itl.train.service;

import ru.itl.train.dto.ArrivalWagon;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 29.07.2023
 */
public interface ArrivalWagonService {
    ArrivalWagon update(ArrivalWagon arrivalWagon);

    Long delete(Long id);

    List<ArrivalWagon> getAll();

    Optional<ArrivalWagon> getById(Long id);

    ArrivalWagon save(ArrivalWagon arrivalWagon);

}
