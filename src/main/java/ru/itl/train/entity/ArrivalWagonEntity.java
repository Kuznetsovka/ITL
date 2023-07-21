package ru.itl.train.entity;

import lombok.*;

import javax.persistence.*;
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
@Entity
@Table(name = "tbl_arrival_wagon")
public class ArrivalWagonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private TechInfoEntity wagonInfo;

    @Column(name = "nomenclature_cargo")
    private String nomenclatureCargo;

    @Column(name = "weight_cargo")
    private BigDecimal weightCargo;


}
