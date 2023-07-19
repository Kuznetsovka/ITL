package ru.itl.train.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * Натурный лист для приема вагонов (Список вагонов с атрибутами:
 * Порядковый номер, Номер вагона, Номенклатура груза, Вес груза в вагоне, Вес вагона)
 * @author Kuznetsovka 19.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArrivalWagon {

    private Long id;
    private Long number;
    private BigDecimal weightWagon;
    private String nomenclatureCargo;
    private BigDecimal weightCargo;


}
