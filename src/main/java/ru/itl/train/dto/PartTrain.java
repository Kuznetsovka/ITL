package ru.itl.train.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kuznetsovka 24.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartTrain {

    private Long order;
    private Wagon wagon;
}
