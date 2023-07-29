package ru.itl.train.service;

import ru.itl.train.dto.Wagon;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 29.07.2023
 */
public interface WagonService {
    Wagon update(Wagon wagon);

    Long delete(Long id);

    List<Wagon> getAll();

    Optional<Wagon> getById(Long id);

    Wagon save(Wagon wagon);
}
