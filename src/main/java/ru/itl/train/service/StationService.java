package ru.itl.train.service;

import ru.itl.train.dto.Station;
import ru.itl.train.dto.Wagon;
import ru.itl.train.entity.MapTrainEntity;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.service.enums.SideTrain;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 29.07.2023
 */
public interface StationService {
    Station update(Station station);

    Long delete(Long id);

    List<Station> getAll();

    Optional<Station> getById(Long id);

    Station save(Station station);

    Optional<RoadEntity> checkRoadOnStation(Long number, Long id);

    List<MapTrainEntity> checkTrainOnStationByRoad(Long number, List<Wagon> partTrains, SideTrain side);

    List<MapTrainEntity> checkPlaceWagons(SideTrain side, List<MapTrainEntity> mapTrainEntities, List<Long> orders);

    boolean checkMissingWagon(List<Long> orders);

    List<MapTrainEntity> checkPlaceWagonAndOrdering(List<Wagon> wagons, SideTrain head);
}
