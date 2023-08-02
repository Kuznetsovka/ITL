package ru.itl.train.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Паспорт вагонов (Номер, Тип, Вес тары, Грузоподъемность)
 * @author Kuznetsovka 19.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wagon {
    private Long id;
    private Long number;
    private String type;
    private BigDecimal weightWagon;
    private BigDecimal loadCapacity;

    public static String getListNumberWagon(List<Wagon> wagons) {
        String numberWagons = wagons.stream()
                .map(Wagon::getNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return numberWagons;
    }
}
