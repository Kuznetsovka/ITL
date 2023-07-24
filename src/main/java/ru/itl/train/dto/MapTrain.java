package ru.itl.train.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Deque;

/**
 * @author Kuznetsovka 24.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapTrain {

    private Long id;

    private Road road;

    private Deque<PartTrain> orderWagon;

}
