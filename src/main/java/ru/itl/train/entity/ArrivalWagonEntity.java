package ru.itl.train.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "tbl_receiving_wagon",
        indexes = @Index(name = "idx_tbl_receiving_wagon_wagon", columnList = "wagon"))
public class ArrivalWagonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "wagon_number")),
            @AttributeOverride(name = "weightWagon", column = @Column(name = "weight_wagon")),
    })
    private WagonEntity wagon;

    @Column(name = "nomenclature_cargo")
    private String nomenclatureCargo;

    @Column(name = "weight_cargo")
    private BigDecimal weightCargo;


}
