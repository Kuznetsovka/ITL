package ru.itl.train.service;

import ru.itl.train.dto.PartTrain;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 24.07.2023
 */
public interface PartTrainService {

    PartTrain update(PartTrain partTrain);

    String delete(Long order, Long wagonNumber);

    List<PartTrain> getAll();

    Optional<PartTrain> getByOrderAndWagonNumber(Long order, Long wagonNumber);

    PartTrain save(PartTrain partTrain);
}
