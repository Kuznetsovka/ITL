package ru.itl.train.utils;

import ru.itl.train.dto.ArrivalWagonPojo;
import ru.itl.train.dto.ChangeWagonPojo;
import ru.itl.train.dto.Road;
import ru.itl.train.dto.Wagon;

import java.util.List;

/**
 * @author Kuznetsovka 02.08.2023
 */
public class MessageUtils {

    public static final String MSG = "Вагоны не могут быть приняты!";

    public static final String ERROR_WAGONS = "В json не указаны вагоны.";

    public static final String ERROR_MAP_TRAIN = "В json не указан перемещаемый состав.";
    public static final String ERROR_ROAD_AND_WAGONS = "В json не указан путь или не указан номер.";
    public static final String ERROR_STATION = "В json не указана станция или не указан id станции.";
    public static final String RESULT_ERROR_MSG_ROAD_ON_ANOTHER_STATION = "Указанный путь не существует на указанной станции";
    public static final String RESULT_ERROR_MSG_TRAIN_ON_ANOTHER_STATION = "Указанный состав и путь находятся на разных станциях или вагоны находятся в середине состава";
    public static final String RESULT_ERROR_MSG_WAGON_NOT_HEAD = "Указанные вагоны находятся не в голове состава";

    public static String checkArrivalAction(ArrivalWagonPojo arrivalWagonPojo) {

        if (arrivalWagonPojo.getStation() == null || arrivalWagonPojo.getStation().getId() == null) {
            return String.join("", MSG, ERROR_STATION);
        }
        String res = checkListsRoadAndWagon(arrivalWagonPojo.getRoad(), arrivalWagonPojo.getWagons(), ERROR_ROAD_AND_WAGONS);
        return res;
    }

    private static String checkListsRoadAndWagon(Road arrivalWagonPojo, List<Wagon> pojo, String msg) {

        if (arrivalWagonPojo == null || arrivalWagonPojo.getNumber() == null) {
            return String.join("", MSG, msg);
        }
        return checkListWagons(pojo);
    }

    public static String checkListWagons(List<Wagon> wagons) {

        if (wagons == null || wagons.isEmpty()) {
            return String.join("", MSG, ERROR_WAGONS);
        }
        return null;
    }

    public static String checkArrivalAction(ChangeWagonPojo changeWagonPojo) {
        String res = checkListsRoadAndWagon(changeWagonPojo.getRoad(), changeWagonPojo.getWagons(), ERROR_MAP_TRAIN);
        return res;
    }
}
