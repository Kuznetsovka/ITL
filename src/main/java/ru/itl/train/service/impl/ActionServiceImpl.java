package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.*;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.service.ActionService;
import ru.itl.train.service.MapTrainService;
import ru.itl.train.service.StationService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 29.07.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final StationService stationService;

    private final MapTrainService mapTrainService;

    @Transactional
    @Override
    public String addArrivalWagons(ArrivalWagonPojo arrivalWagonPojo) {
        String msg = checkArrivalAction(arrivalWagonPojo);
        if (msg != null) {
            log.error(msg);
            return msg;
        }
        Optional<RoadEntity> road = stationService.checkRoadOnStation(arrivalWagonPojo.getRoad().getNumber(), arrivalWagonPojo.getStation().getId());
        if (road.isEmpty()) {
            return "Вагоны не могут быть приняты! Указанный путь не существует на указанной станции";
        }
        List<MapTrain> mapTrain = mapTrainService.addWagons(road.get(), arrivalWagonPojo.getWagons());
        if (mapTrain.isEmpty()) {
            return "Вагоны не могут быть приняты! Указанный путь не существует на указанной станции";
        }
        String numberWagons = arrivalWagonPojo.getWagons().stream()
                .map(Wagon::getNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String numberStation = arrivalWagonPojo.getStation().getName();
        if (numberStation == null) {
            numberStation = stationService.getById(arrivalWagonPojo.getStation().getId()).get().getName();
        }
        msg = String.format("Вагоны [%s] приняты на станцию [%s]!", numberWagons, numberStation);
        return msg;
    }

    @Transactional
    @Override
    public String changeWagons(ChangeWagonPojo arrivalWagonPojo) {
        String msg = checkArrivalAction(arrivalWagonPojo);
        if (msg != null) {
            log.error(msg);
            return msg;
        }
        boolean wagonAndRoadOnDifferentStation = stationService.checkTrainOnStationByRoad(arrivalWagonPojo.getRoad().getNumber(), arrivalWagonPojo.getPartTrains());
        if (wagonAndRoadOnDifferentStation) {
            return "Вагоны не могут быть приняты! Указанный состав и путь находятся на разных станциях";
        }
        arrivalWagonPojo.getPartTrains().sort(Comparator.comparing(PartTrain::getOrder));
        List<Wagon> sortedChangeWagon = arrivalWagonPojo.getPartTrains().stream()
                .map(PartTrain::getWagon)
                .collect(Collectors.toList());

        Long minOrder = arrivalWagonPojo.getPartTrains().stream().min(Comparator.comparing(PartTrain::getOrder)).get().getOrder();

//        Queue<PartTrainEntity> queue = changingMapTrain.get().getOrderWagon().stream().sorted(Comparator.comparing(PartTrainEntity::getOrder)).collect(Collectors.toCollection(ArrayDeque::new));

        // Проверка нахождения вагонов в составе.
        return null;
    }

    @Override
    public String departureWagons(ArrivalWagonPojo arrivalWagonPojo) {
        return null;
    }

    private String checkArrivalAction(ArrivalWagonPojo arrivalWagonPojo) {
        String msg = "Вагоны не могут быть приняты!";
        if (arrivalWagonPojo.getStation() == null || arrivalWagonPojo.getStation().getId() == null) {
            return String.join("", msg, "В json не указана станция или не указан id станции.");
        }
        if (arrivalWagonPojo.getRoad() == null || arrivalWagonPojo.getRoad().getNumber() == null) {
            return String.join("", msg, "В json не указан путь или не указан номер.");
        }
        if (arrivalWagonPojo.getWagons() == null || arrivalWagonPojo.getWagons().isEmpty()) {
            return String.join("", msg, "В json не указаны вагоны.");
        }
        return null;
    }

    private String checkArrivalAction(ChangeWagonPojo changeWagonPojo) {
        String msg = "Вагоны не могут быть приняты!";
        if (changeWagonPojo.getRoad() == null || changeWagonPojo.getRoad().getNumber() == null) {
            return String.join("", msg, "В json не указан путь или не указан номер.");
        }
        if (changeWagonPojo.getPartTrains() == null || changeWagonPojo.getPartTrains().isEmpty()) {
            return String.join("", msg, "В json не указан перемещаемый состав.");
        }
        return null;
    }
}
