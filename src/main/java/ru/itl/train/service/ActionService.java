package ru.itl.train.service;

import ru.itl.train.dto.ArrivalWagonPojo;
import ru.itl.train.dto.ChangeWagonPojo;

/**
 * @author Kuznetsovka 29.07.2023
 */
public interface ActionService {
    String addArrivalWagons(ArrivalWagonPojo arrivalWagonPojo);

    String changeWagons(ChangeWagonPojo changeWagonPojo);

    String departureWagons(ArrivalWagonPojo arrivalWagonPojo);

}
