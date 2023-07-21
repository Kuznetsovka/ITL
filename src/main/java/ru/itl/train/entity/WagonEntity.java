package ru.itl.train.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Паспорт вагонов (Номер, Тип, Вес тары, Грузоподъемность)
 * @author Kuznetsovka 19.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_wagon",
        indexes =  @Index(name = "idx_tbl_wagon_number", columnList = "number"))
public class WagonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Embedded
    private TechInfoEntity wagonInfo;

    /** Грузоподъемность, в тоннах */
    @Column(name = "load_capacity", nullable = false)
    private BigDecimal loadCapacity;

}
