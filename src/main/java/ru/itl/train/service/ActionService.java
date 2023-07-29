package ru.itl.train.service;

import ru.itl.train.dto.ArrivalWagonPojo;

/**
 * @author Kuznetsovka 29.07.2023
 */
public interface ActionService {
    String addArrivalWagons(ArrivalWagonPojo arrivalWagonPojo);

    String changeWagons(ArrivalWagonPojo arrivalWagonPojo);

    String departureWagons(ArrivalWagonPojo arrivalWagonPojo);

}
