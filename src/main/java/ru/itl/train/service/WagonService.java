package ru.itl.train.service;

import ru.itl.train.dto.Wagon;
import ru.itl.train.entity.WagonEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 23.07.2023
 */
public interface WagonService {
    Wagon update(Wagon wagon);

    Long delete(Long id);

    List<Wagon> getAllWagons();

    Optional<Wagon> getById(Long id);

    Wagon dtoFromEntity(WagonEntity entity);

    WagonEntity entityFromDto(Wagon dto);

    Wagon save(Wagon wagon);
}
