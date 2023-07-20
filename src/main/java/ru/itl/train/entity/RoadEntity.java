package ru.itl.train.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Путь
 * @author Kuznetsovka 19.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_road",
        uniqueConstraints=@UniqueConstraint(
                name = "tbl_road_number_station_idx", columnNames={"number", "station"}))
public class RoadEntity {

    @Id
    @Column(unique = true, nullable = false)
    private Long number;

    @ManyToOne(fetch = FetchType.LAZY)
    private StationEntity station;
}
