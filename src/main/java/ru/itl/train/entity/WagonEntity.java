package ru.itl.train.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Embeddable
@Table(name = "tbl_wagon",
        uniqueConstraints=@UniqueConstraint(
                name = "bk_tbl_wagon_number_idx", columnNames={"number"}))
public class WagonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long number;

    private String type;

    /** Вес тары: вес вагона, в тоннах */
    @Column(name = "weight_wagon", nullable = false)
    private BigDecimal weightWagon;

    /** Грузоподъемность, в тоннах */
    @Column(name = "load_capacity", nullable = false)
    private BigDecimal loadCapacity;


}
