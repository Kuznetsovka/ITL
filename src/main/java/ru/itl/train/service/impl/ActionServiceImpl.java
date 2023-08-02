package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.ArrivalWagonPojo;
import ru.itl.train.dto.ChangeWagonPojo;
import ru.itl.train.dto.MapTrain;
import ru.itl.train.dto.Wagon;
import ru.itl.train.entity.MapTrainEntity;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.service.ActionService;
import ru.itl.train.service.MapTrainService;
import ru.itl.train.service.RoadService;
import ru.itl.train.service.StationService;
import ru.itl.train.service.enums.SideTrain;
import ru.itl.train.utils.MessageUtils;

import java.util.List;
import java.util.Optional;

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
        String msg = MessageUtils.checkArrivalAction(arrivalWagonPojo);
        if (msg != null) {
            log.error(msg);
            return msg;
        }
        Optional<RoadEntity> road = stationService.checkRoadOnStation(arrivalWagonPojo.getRoad().getNumber(), arrivalWagonPojo.getStation().getId());
        if (road.isEmpty()) {
            return String.join(" ", MessageUtils.MSG, MessageUtils.RESULT_ERROR_MSG_ROAD_ON_ANOTHER_STATION);
        }
        List<MapTrain> mapTrain = mapTrainService.addWagons(road.get(), arrivalWagonPojo.getWagons());
        if (mapTrain.isEmpty()) {
            return String.join(" ", MessageUtils.MSG, MessageUtils.RESULT_ERROR_MSG_ROAD_ON_ANOTHER_STATION);
        }
        String numberWagons = Wagon.getListNumberWagon(arrivalWagonPojo.getWagons());
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
        String msg = MessageUtils.checkArrivalAction(changeWagonPojo);
        if (msg != null) {
            log.error(msg);
            return msg;
        }
        List<MapTrainEntity> mapTrainOnRoad = stationService.checkTrainOnStationByRoad(changeWagonPojo.getRoad().getNumber(), changeWagonPojo.getWagons(), SideTrain.BOTH);
        if (mapTrainOnRoad.isEmpty()) {
            return String.join(" ", MessageUtils.MSG, MessageUtils.RESULT_ERROR_MSG_TRAIN_ON_ANOTHER_STATION);
        }

        boolean isChange = mapTrainService.changeWagons(mapTrainOnRoad, changeWagonPojo.getRoad());
        if (!isChange)
            return MessageUtils.MSG;

        String numberWagons = Wagon.getListNumberWagon(changeWagonPojo.getWagons());

        Long numberStation = changeWagonPojo.getRoad().getNumber();
        if (numberStation == null) {
            numberStation = roadService.getById(changeWagonPojo.getRoad().getNumber()).get().getNumber();
        }
        msg = String.format("Вагоны [%s] были перенесены на путь номер [%d]!", numberWagons, numberStation);
        return msg;
    }

    @Override
    public String departureWagons(List<Wagon> wagons) {
        String msg = MessageUtils.checkListWagons(wagons);
        if (msg != null) {
            log.error(msg);
            return msg;
        }
        List<MapTrainEntity> mapTrains = stationService.checkPlaceWagonAndOrdering(wagons, SideTrain.HEAD);
        if (mapTrains.isEmpty()) {
            return String.join(" ", MessageUtils.MSG, MessageUtils.RESULT_ERROR_MSG_WAGON_NOT_HEAD);
        }
        mapTrainService.departureWagons(mapTrains);

        String numberWagons = Wagon.getListNumberWagon(wagons);

        msg = String.format("Вагоны [%s] отбыли на пути РЖД!", numberWagons);
        return msg;
    }
}
