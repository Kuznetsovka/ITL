package ru.itl.train.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Kuznetsovka 29.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeWagonPojo {
    private List<PartTrain> partTrains;
    private Road road;
}
