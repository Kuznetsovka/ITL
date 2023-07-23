package ru.itl.train.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Kuznetsovka 21.07.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class TechInfoEntity {

    @Column(name = "number")
    private Long number;

    @Column(name = "weight_wagon", nullable = false)
    private BigDecimal weightWagon;

}
