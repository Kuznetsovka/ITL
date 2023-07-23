package ru.itl.train.service;

import ru.itl.train.dto.Road;
import ru.itl.train.entity.RoadEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 23.07.2023
 */
public interface RoadService {
    Road update(Road road);

    Long delete(Long id);

    List<Road> getAllRoads();

    Optional<Road> getById(Long id);

    Road dtoFromEntity(RoadEntity entity);

    RoadEntity entityFromDto(Road dto);

    Road save(Road road);
}
