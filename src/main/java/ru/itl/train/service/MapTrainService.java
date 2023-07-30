package ru.itl.train.service;

import ru.itl.train.dto.MapTrain;
import ru.itl.train.dto.PartTrain;
import ru.itl.train.dto.Wagon;
import ru.itl.train.entity.MapTrainEntity;
import ru.itl.train.entity.RoadEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 29.07.2023
 */
public interface MapTrainService {
    MapTrain update(MapTrain mapTrain);

    Long delete(Long id);

    List<MapTrain> getAll();

    Optional<MapTrain> getById(Long id);

    MapTrain save(MapTrain mapTrain);

    Optional<MapTrain> addWagons(RoadEntity roadNumber, List<Wagon> wagons);

    Optional<MapTrainEntity> getRoadByPartTrains(List<PartTrain> partTrains);
}
