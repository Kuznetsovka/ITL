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
                name = "bk_tbl_road_number_station_id_idx", columnNames={"number", "station_id"}))
public class RoadEntity {

    @Id
    @Column(nullable = false)
    private Long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="station_id",
            foreignKey=@ForeignKey(name = "fk_tbl_road_station"))
    private StationEntity station;
}
