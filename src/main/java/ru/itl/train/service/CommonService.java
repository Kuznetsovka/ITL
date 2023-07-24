package ru.itl.train.service;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 23.07.2023
 */
public interface CommonService<T> {
    T update(T road);

    Long delete(Long id);

    List<T> getAll();

    Optional<T> getById(Long id);

    T save(T road);
}
