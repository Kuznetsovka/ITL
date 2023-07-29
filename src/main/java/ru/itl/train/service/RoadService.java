package ru.itl.train.service;

import ru.itl.train.dto.Road;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 29.07.2023
 */
public interface RoadService {
    Road update(Road road);

    Long delete(Long id);

    List<Road> getAll();

    Optional<Road> getById(Long id);

    Road save(Road road);
}
