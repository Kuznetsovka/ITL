package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.ArrivalWagonPojo;
import ru.itl.train.dto.MapTrain;
import ru.itl.train.dto.Wagon;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.service.ActionService;
import ru.itl.train.service.MapTrainService;
import ru.itl.train.service.RoadService;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 29.07.2023
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class ActionServiceImpl implements ActionService {

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
        Optional<RoadEntity> road = roadService.checkRoadOnStation(arrivalWagonPojo.getRoad().getNumber(), arrivalWagonPojo.getStation().getId());
        if (road.isEmpty()) {
            return String.join(".", msg, "Указанный путь не существует на указанной станции");
        }
        Optional<MapTrain> mapTrain = mapTrainService.addWagons(road.get(), arrivalWagonPojo.getWagons());
        String numberWagons = arrivalWagonPojo.getWagons().stream()
                .map(Wagon::getNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String numberStation = arrivalWagonPojo.getStation().getName();
        msg = String.format("Вагоны [%s] приняты на станцию [%s]!", numberWagons, numberStation);
        return msg;
    }

    private String checkArrivalAction(ArrivalWagonPojo arrivalWagonPojo) {
        String msg = "Вагоны не могут быть приняты!";
        if (arrivalWagonPojo.getStation() == null || arrivalWagonPojo.getStation().getId() == null) {
            return String.join(".", msg, "В json не указана станция прибытия или не указан id станции.");
        }
        if (arrivalWagonPojo.getRoad() == null || arrivalWagonPojo.getRoad().getNumber() == null) {
            return String.join(".", msg, "В json не указан путь прибытия или не указан номер.");
        }
        if (arrivalWagonPojo.getWagons() == null || arrivalWagonPojo.getWagons().isEmpty()) {
            return String.join(".", msg, "В json не указаны вагоны.");
        }
        return null;
    }

    @Override
    public String changeWagons(ArrivalWagonPojo arrivalWagonPojo) {
        return null;
    }

    @Override
    public String departureWagons(ArrivalWagonPojo arrivalWagonPojo) {
        return null;
    }
}
