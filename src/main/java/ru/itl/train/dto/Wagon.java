package ru.itl.train.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

}
