package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.*;
import ru.itl.train.entity.MapTrainEntity;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.service.ActionService;
import ru.itl.train.service.MapTrainService;
import ru.itl.train.service.RoadService;
import ru.itl.train.service.StationService;

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

    private final RoadService roadService;

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
    public String changeWagons(ChangeWagonPojo changeWagonPojo) {
        String msg = checkArrivalAction(changeWagonPojo);
        if (msg != null) {
            log.error(msg);
            return msg;
        }
        List<MapTrainEntity> mapTrainOnRoad = stationService.checkTrainOnStationByRoad(changeWagonPojo.getRoad().getNumber(), changeWagonPojo.getWagons());
        if (mapTrainOnRoad.isEmpty()) {
            return "Вагоны не могут быть приняты! Указанный состав и путь находятся на разных станциях или вагоны находятся в середине состава";
        }

        mapTrainService.changeWagons(mapTrainOnRoad, changeWagonPojo.getRoad());

        String numberWagons = changeWagonPojo.getWagons().stream()
                .map(Wagon::getNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        Long numberStation = changeWagonPojo.getRoad().getNumber();
        if (numberStation == null) {
            numberStation = roadService.getById(changeWagonPojo.getRoad().getNumber()).get().getNumber();
        }
        msg = String.format("Вагоны [%s] были перенесены на путь номер [%d]!", numberWagons, numberStation);
        return msg;
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
        String res = checkListsRoadAndWagon(arrivalWagonPojo.getRoad(), msg, arrivalWagonPojo.getWagons(), "В json не указаны вагоны.");
        return res;
    }

    private static String checkListsRoadAndWagon(Road arrivalWagonPojo, String msg, List<Wagon> arrivalWagonPojo1, String x) {
        if (arrivalWagonPojo == null || arrivalWagonPojo.getNumber() == null) {
            return String.join("", msg, "В json не указан путь или не указан номер.");
        }
        if (arrivalWagonPojo1 == null || arrivalWagonPojo1.isEmpty()) {
            return String.join("", msg, x);
        }
        return null;
    }

    private String checkArrivalAction(ChangeWagonPojo changeWagonPojo) {
        String msg = "Вагоны не могут быть приняты!";
        String res = checkListsRoadAndWagon(changeWagonPojo.getRoad(), msg, changeWagonPojo.getWagons(), "В json не указан перемещаемый состав.");
        return res;
    }
}
